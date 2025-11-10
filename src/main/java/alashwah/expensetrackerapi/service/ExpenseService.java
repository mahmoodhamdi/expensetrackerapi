package alashwah.expensetrackerapi.service;

import alashwah.expensetrackerapi.entity.Expense;

import java.util.List;
public interface ExpenseService {
    List<Expense>getAllExpenses();
    Expense getExpenseById(Long id);
    void  deleteExpenseById(Long id);
    Expense saveExpense(Expense expense);
    Expense updateExpense(Long id, Expense expense);


}
