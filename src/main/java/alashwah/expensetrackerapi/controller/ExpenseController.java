package alashwah.expensetrackerapi.controller;

import alashwah.expensetrackerapi.entity.Expense;
import alashwah.expensetrackerapi.service.ExpenseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpenseController {
    final  private ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
@GetMapping("/expenses")
public List<Expense> getAllExpense(){
    return expenseService.getAllExpenses();

}
@GetMapping("/expenses/{id}")
    public Expense getExpenseById(@PathVariable("id") Long id){
        return expenseService.getExpenseById(id);
}

}
