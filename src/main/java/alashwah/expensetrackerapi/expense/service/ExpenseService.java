package alashwah.expensetrackerapi.expense.service;

import alashwah.expensetrackerapi.expense.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
public interface ExpenseService {
    Page<Expense> getFilteredExpenses(
            String expenseName,
            String description,
            Double minAmount,
            Double maxAmount,
            String category,
            LocalDate startDate,
            LocalDate endDate,
            LocalDateTime createdAfter,
            LocalDateTime createdBefore,
            Pageable pageable
    );

    Expense getExpenseById(Long id);
    void  deleteExpenseById(Long id);
    Expense saveExpense(Expense expense);
    Expense updateExpense(Long id, Expense expense);
    Page<Expense> getAllExpenses(Pageable pageable);

}
