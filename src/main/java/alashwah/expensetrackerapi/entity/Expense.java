package alashwah.expensetrackerapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_expenses")
@EntityListeners(AuditingEntityListener.class)
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Expense name cannot be empty")
    @Size(max = 100, message = "Expense name must be less than 100 characters")
    @Column(nullable = false)
    private String expenseName;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 255, message = "Description must be less than 255 characters")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "Expense amount is required")
    @Positive(message = "Expense amount must be positive")
    @Column(nullable = false)
    private Double expenseAmount;

    @NotBlank(message = "Category cannot be empty")
    @Size(max = 50, message = "Category must be less than 50 characters")
    @Column(nullable = false)
    private String category;

    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDate date;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
