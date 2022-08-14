package thien.util.core;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class QuerySpecific<T> implements Specification<T> {
    private final Map<String, Object> query;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(query !=null && !query.isEmpty()){
            for(Map.Entry<String, Object> prop : query.entrySet()) {
                String key = prop.getKey();
                Object value = prop.getValue();
                predicates.add(criteriaBuilder.equal(
                        root.get(key),
                        value
                ));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
