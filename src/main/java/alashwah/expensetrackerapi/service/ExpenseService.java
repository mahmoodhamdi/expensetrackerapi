package alashwah.expensetrackerapi.service;

import alashwah.expensetrackerapi.entity.Expense;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ExpenseService {
    List<Expense>getAllExpenses();
    Expense getExpenseById(Long id);
    void  deleteExpenseById(Long id);

}
