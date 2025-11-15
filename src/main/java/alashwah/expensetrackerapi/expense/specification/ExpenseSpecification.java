package alashwah.expensetrackerapi.expense.specification;

import alashwah.expensetrackerapi.expense.entity.Expense;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpenseSpecification {

    public static Specification<Expense> belongsToUser(Long userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Expense> filterBy(
            String expenseName,
            String description,
            Double minAmount,
            Double maxAmount,
            String category,
            LocalDate startDate,
            LocalDate endDate,
            LocalDateTime createdAfter,
            LocalDateTime createdBefore
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by expense name (contains - case insensitive)
            if (expenseName != null && !expenseName.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("expenseName")),
                        "%" + expenseName.toLowerCase() + "%"
                ));
            }

            // Filter by description (contains - case insensitive)
            if (description != null && !description.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")),
                        "%" + description.toLowerCase() + "%"
                ));
            }

            // Filter by minimum amount
            if (minAmount != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("expenseAmount"),
                        minAmount
                ));
            }

            // Filter by maximum amount
            if (maxAmount != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("expenseAmount"),
                        maxAmount
                ));
            }

            // Filter by category (exact match - case insensitive)
            if (category != null && !category.isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("category")),
                        "%" + category.toLowerCase() + "%"
                ));
            }

            // Filter by start date (date >= startDate)
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("date"),
                        startDate
                ));
            }

            // Filter by end date (date <= endDate)
            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("date"),
                        endDate
                ));
            }

            // Filter by created after
            if (createdAfter != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("createdAt"),
                        createdAfter
                ));
            }

            // Filter by created before
            if (createdBefore != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("createdAt"),
                        createdBefore
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}