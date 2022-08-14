package thien.util.core;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class SearchSpecific<T> implements Specification<T> {
    private final Map<String, Object> search;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(search !=null && !search.isEmpty()){
            for(Map.Entry<String, Object> prop : search.entrySet()) {
                String key = prop.getKey();
                String value =(String) prop.getValue();
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(key)),
                        "%"+value.toString().toUpperCase()+"%"
                ));
                break;
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
