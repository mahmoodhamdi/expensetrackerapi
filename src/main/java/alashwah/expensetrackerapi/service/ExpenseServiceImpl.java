package alashwah.expensetrackerapi.service;

import alashwah.expensetrackerapi.entity.Expense;
import alashwah.expensetrackerapi.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + id));
    }

    @Override
    public void deleteExpenseById(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete — expense not found with id " + id);
        }
        expenseRepository.deleteById(id);
    }

    @Override
    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);

        Optional.ofNullable(expense.getExpenseName())
                .ifPresent(existingExpense::setExpenseName);

        Optional.ofNullable(expense.getDescription())
                .ifPresent(existingExpense::setDescription);

        Optional.ofNullable(expense.getExpenseAmount())
                .ifPresent(existingExpense::setExpenseAmount);

        Optional.ofNullable(expense.getCategory())
                .ifPresent(existingExpense::setCategory);

        Optional.ofNullable(expense.getDate())
                .ifPresent(existingExpense::setDate);

        return expenseRepository.save(existingExpense);
    }
}
