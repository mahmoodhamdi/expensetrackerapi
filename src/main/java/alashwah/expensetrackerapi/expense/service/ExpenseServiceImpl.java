package alashwah.expensetrackerapi.expense.service;

import alashwah.expensetrackerapi.expense.entity.Expense;
import alashwah.expensetrackerapi.exception.ResourceNotFoundException;
import alashwah.expensetrackerapi.expense.repository.ExpenseRepository;
import alashwah.expensetrackerapi.expense.specification.ExpenseSpecification;
import alashwah.expensetrackerapi.security.service.CustomUserDetails;
import alashwah.expensetrackerapi.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private User getCurrentUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userDetails.getUser();
    }

    @Override
    public Page<Expense> getAllExpenses(Pageable pageable) {
        User currentUser = getCurrentUser();
        return expenseRepository.findAll(
                ExpenseSpecification.belongsToUser(currentUser.getId()),
                pageable
        );
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
        User currentUser = getCurrentUser();
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
        ).and(ExpenseSpecification.belongsToUser(currentUser.getId()));

        return expenseRepository.findAll(spec, pageable);
    }

    @Override
    public Expense getExpenseById(Long id) {
        User currentUser = getCurrentUser();
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense", "id", id));

        if (!expense.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Expense not found or you don't have permission to access it");
        }

        return expense;
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = getExpenseById(id); // This checks ownership
        expenseRepository.deleteById(id);
    }

    @Override
    public Expense saveExpense(Expense expense) {
        User currentUser = getCurrentUser();
        expense.setUser(currentUser);
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id); // This checks ownership

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