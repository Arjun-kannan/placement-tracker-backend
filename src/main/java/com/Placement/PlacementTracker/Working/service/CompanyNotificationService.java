package com.Placement.PlacementTracker.Working.service;

import com.Placement.PlacementTracker.Working.dto.CompanyNotificationResponse;
import com.Placement.PlacementTracker.Working.model.CompanyNotification;
import com.Placement.PlacementTracker.Working.model.StudentApplication;
import com.Placement.PlacementTracker.Working.model.StudentDetails;
import com.Placement.PlacementTracker.Working.repository.CompanyNotificationRepository;
import com.Placement.PlacementTracker.Working.repository.StudentApplicationRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CompanyNotificationService {

    private final CompanyNotificationRepository companyRepository;
    private final StudentApplicationRepository applicationRepository;

    public CompanyNotification addCompany(CompanyNotification notification) {
        return companyRepository.save(notification);
    }

    public Page<CompanyNotificationResponse> findAllCompanies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompanyNotification> companyPage = companyRepository.findAll(pageable);

        return companyPage.map(company -> {
            return CompanyNotificationResponse.builder()
                    .id(company.getId())
                    .companyName(company.getCompanyName())
                    .jobDescription(company.getJobDescription())
                    .requiredCgpa(company.getRequiredCgpa())
                    .allowBacklogHistory(company.isAllowBacklogHistory())
                    .allowBacklogHistory(company.isAllowBacklogHistory())
                    .slab(company.getSlab().toString())
                    .formLink(company.getFormLink())
                    .timestamp(company.getTimestamp())
                    .validity(company.getValidity())
                    .isEligible(true)
                    .hasApplied(false)
                    .applicationCount(company.getApplicationCount())
                    .build();
        });
    }

//    public Page<CompanyNotificationResponse> findAllCompanies(StudentDetails student, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);

    /// /        System.out.println("ABCDEF" + (companyRepository.findAllByCriteria(student.getId(), student.getCgpa(), student.getActiveBacklog(), student.isBacklogHistory(), pageable)).get());
    /// /        return companyRepository.findAllByCriteria(student.getId(), student.getCgpa(), student.getActiveBacklog(), student.isBacklogHistory(), pageable);
//        Page<CompanyNotification> companyPage = companyRepository.findAll(pageable);
//
//        return companyPage.map(company -> {
//            return CompanyNotificationResponse.builder()
//                    .id(company.getId())
//                    .companyName(company.getCompanyName())
//                    .jobDescription(company.getJobDescription())
//                    .requiredCgpa(company.getRequiredCgpa())
//                    .allowBacklogHistory(company.isAllowBacklogHistory())
//                    .allowBacklogHistory(company.isAllowBacklogHistory())
//                    .slab(company.getSlab().toString())
//                    .formLink(company.getFormLink())
//                    .timestamp(company.getTimestamp())
//                    .validity(company.getValidity())
//                    .isEligible(true)
//                    .hasApplied(false)
//                    .applicationCount(company.getApplicationCount())
//                    .build();
//        });
//    }
    public Page<CompanyNotificationResponse> findAllCompanies(StudentDetails student, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CompanyNotification> companyPage = companyRepository.findAll(pageable);

        // Fetch the student's applications once to avoid N+1 queries
        List<StudentApplication> studentApplications = applicationRepository.findByStudentId(student.getId());

        return companyPage.map(company -> {
            // Check eligibility
            boolean isEligible = checkEligibility(student, company);

            // Check if student has applied
            boolean hasApplied = checkHasApplied(student.getId(), company.getId(), studentApplications);

            boolean isValid = checkValidity(company);

            // Get application count (you might want to cache this if it's expensive)
            int applicationCount = company.getApplicationCount();

            return CompanyNotificationResponse.builder()
                    .id(company.getId())
                    .companyName(company.getCompanyName())
                    .jobDescription(company.getJobDescription())
                    .requiredCgpa(company.getRequiredCgpa())
                    .allowBacklogHistory(company.isAllowBacklogHistory())
                    .allowActiveBacklog(company.getAllowActiveBacklog()) // Fixed duplicate field
                    .slab(company.getSlab().toString())
                    .formLink(company.getFormLink())
                    .timestamp(company.getTimestamp())
                    .validity(company.getValidity())
                    .isEligible(isEligible)
                    .hasApplied(hasApplied)
                    .isValid(isValid)
                    .applicationCount(applicationCount)
                    .build();
        });
    }

    private boolean checkValidity(CompanyNotification company) {
        Date postedTime = company.getTimestamp();
        int validity = company.getValidity();

        Calendar expiry = Calendar.getInstance();
        expiry.setTime(postedTime);
        expiry.add(Calendar.HOUR, validity);

        Date now = new Date();
        return now.before(expiry.getTime());
    }

    private boolean checkEligibility(StudentDetails student, CompanyNotification company) {
        // Check CGPA requirement
        if (student.getCgpa() < company.getRequiredCgpa()) {
            return false;
        }

        // Check active backlogs
        if (student.getActiveBacklog() > company.getAllowActiveBacklog()) {
            return false;
        }

        // Check backlog history
        if (!company.isAllowBacklogHistory() && student.isBacklogHistory()) {
            return false;
        }

        return true;
    }

    private boolean checkHasApplied(int studentId, int companyId, List<StudentApplication> studentApplications) {
        // Check if student has already applied to this company
        return studentApplications.stream()
                .anyMatch(app -> app.getCompany().getId() == companyId);
    }

    public void exportCompanyApplicants(int companyId, HttpServletResponse response) throws IOException {
        CompanyNotification company = companyRepository
                .findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        List<StudentApplication> applicationList = company.getApplications();
        System.out.println("ABCDE" + applicationList);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Applicants");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Applied at");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Email");
        header.createCell(3).setCellValue("CGPA");
        header.createCell(4).setCellValue("Backlog history");
        header.createCell(5).setCellValue("Active Backlog");

        int rowNumber = 1;
        for (StudentApplication app : applicationList) {
            System.out.println("ABCDE "+app.getStudent().getName());
            Row row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue(app.getApplied_at().toString());
            row.createCell(1).setCellValue(app.getStudent().getName());
            row.createCell(2).setCellValue(app.getStudent().getEmail());
            row.createCell(3).setCellValue(app.getStudent().getCgpa());
            row.createCell(4).setCellValue(app.getStudent().isBacklogHistory() ? "yes" : "no");
            row.createCell(5).setCellValue(app.getStudent().getActiveBacklog());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=applicants_company_" + company.getCompanyName() + ".xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();

    }
}
