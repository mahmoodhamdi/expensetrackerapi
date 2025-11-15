package alashwah.expensetrackerapi.expense.service;

import alashwah.expensetrackerapi.expense.entity.Expense;
import alashwah.expensetrackerapi.exception.ResourceNotFoundException;
import alashwah.expensetrackerapi.expense.repository.ExpenseRepository;
import alashwah.expensetrackerapi.expense.specification.ExpenseSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Page<Expense> getAllExpenses(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    @Override
    public Page<Expense> getFilteredExpenses(
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
    ) {
        Specification<Expense> spec = ExpenseSpecification.filterBy(
                expenseName,
                description,
                minAmount,
                maxAmount,
                category,
                startDate,
                endDate,
                createdAfter,
                createdBefore
        );
        return expenseRepository.findAll(spec, pageable);
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));
    }

    @Override
    public void deleteExpenseById(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense", "id", id);
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