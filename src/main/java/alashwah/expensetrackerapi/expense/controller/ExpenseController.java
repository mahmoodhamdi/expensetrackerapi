package alashwah.expensetrackerapi.expense.controller;

import alashwah.expensetrackerapi.expense.entity.Expense;
import alashwah.expensetrackerapi.response.ApiResponse;
import alashwah.expensetrackerapi.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final MessageSource messageSource;

    @Autowired
    public ExpenseController(ExpenseService expenseService, MessageSource messageSource) {
        this.expenseService = expenseService;
        this.messageSource = messageSource;
    }

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Expense>>> getAllExpenses(
            @RequestParam(required = false) String expenseName,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdBefore,
            Pageable pageable
    ) {
        List<Expense> expenses = expenseService.getFilteredExpenses(
                expenseName,
                description,
                minAmount,
                maxAmount,
                category,
                startDate,
                endDate,
                createdAfter,
                createdBefore,
                pageable
        ).toList();

        ApiResponse<List<Expense>> response = ApiResponse.success(
                getMessage("success.expense.fetched"),
                expenses
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Expense>> getExpenseById(@PathVariable Long id) {
        Expense expense = expenseService.getExpenseById(id);
        ApiResponse<Expense> response = ApiResponse.success(
                getMessage("success.expense.fetched"),
                expense
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Expense>> saveExpense(@Valid @RequestBody Expense expense) {
        Expense savedExpense = expenseService.saveExpense(expense);
        ApiResponse<Expense> response = ApiResponse.success(
                getMessage("success.expense.created"),
                savedExpense
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Expense>> updateExpense(@PathVariable Long id, @Valid @RequestBody Expense expense) {
        Expense updated = expenseService.updateExpense(id, expense);
        ApiResponse<Expense> response = ApiResponse.success(
                getMessage("success.expense.updated"),
                updated
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExpenseById(@PathVariable Long id) {
        expenseService.deleteExpenseById(id);
        ApiResponse<Void> response = ApiResponse.success(
                getMessage("success.expense.deleted"),
                null
        );
        return ResponseEntity.ok(response);
    }
}