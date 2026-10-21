package dev.jaderss.expenses.cards;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardRepository repository;

    public CardController(CardRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/search")
    public List<CardView> search(@Valid @RequestBody CardSearch search) {
        return repository.findByHolderCpfOrderById(search.digits()).stream()
                .map(CardView::of)
                .toList();
    }
}
