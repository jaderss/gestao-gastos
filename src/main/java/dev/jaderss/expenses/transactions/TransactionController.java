package dev.jaderss.expenses.transactions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Transaction> record(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = service.record(request);
        return ResponseEntity.created(URI.create("/transactions/" + transaction.id()))
                .body(transaction);
    }

    @GetMapping("/{id}")
    public Transaction get(@PathVariable String id) {
        return service.get(id);
    }

    @PutMapping("/{id}/category")
    public Transaction categorize(@PathVariable String id, @Valid @RequestBody CategoryRequest request) {
        return service.categorize(id, request.category());
    }

    @GetMapping
    public List<Transaction> onDate(
            @RequestParam @Positive Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.onDate(userId, date);
    }

    @GetMapping("/recent")
    public List<Transaction> recent(@RequestParam @Positive Long userId, @RequestParam @Min(1) @Max(366) int days) {
        return service.recent(userId, days);
    }

    @GetMapping("/categories")
    public List<String> categories(
            @RequestParam @Positive Long userId, @RequestParam @NotBlank @Size(max = 50) String prefix) {
        return service.categories(userId, prefix);
    }
}
