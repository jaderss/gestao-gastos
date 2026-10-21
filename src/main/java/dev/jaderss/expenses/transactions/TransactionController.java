package dev.jaderss.expenses.transactions;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public Transaction create(@RequestBody Transaction transaction) {
        return service.create(transaction);
    }

    @GetMapping("/{id}")
    public Transaction get(@PathVariable(name = "id") String id) {
        return service.findById(id).orElseThrow();
    }

    @PostMapping("/{id}/category")
    public Transaction get(
            @PathVariable(name = "id") String id, @RequestBody @Validated @Length(max = 50) String category) {
        return service.categorize(id, category);
    }

    @GetMapping("/recent/{days}")
    public List<Transaction> findRecentByUser(
            @RequestParam(name = "userId") Long userId, @PathVariable("days") Integer days) {
        return service.findRecentByUser(userId, days);
    }

    @GetMapping
    public List<Transaction> findByUserAndDate(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "dataTransaction") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate dataTransaction) {
        return service.findByUserAndDate(userId, dataTransaction);
    }

    @GetMapping("/categories")
    public List<String> findCategories(@RequestParam(name = "prefix") String prefix) {
        return service.findCategories(prefix);
    }
}
