package alashwah.expensetrackerapi.service;

import alashwah.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface ExpenseService {
    Page<Expense> getAllExpenses(Pageable pageable);
    Expense getExpenseById(Long id);
    void  deleteExpenseById(Long id);
    Expense saveExpense(Expense expense);
    Expense updateExpense(Long id, Expense expense);


}
