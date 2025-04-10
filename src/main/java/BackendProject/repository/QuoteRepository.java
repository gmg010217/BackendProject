package BackendProject.repository;

import BackendProject.domain.Exercise;
import BackendProject.domain.Quote;

public interface QuoteRepository {
    Quote findBy(Long quoteId);
}