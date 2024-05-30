package pl.wsb.issuetracker.common.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageWrapperDTO<T> {

    private Collection<T> data;
    private long totalCount;
    private int totalPages;
    private int pageNumber;
    private long offset;
    private long limit;
    private boolean hasNextPage;
    private NextPageDTO next;

    public static <T> PageWrapperDTO<T> from(final Page<T> page) {
        final Pageable pageable = page.getPageable();
        final NextPageDTO next = NextPageDTO.builder()
                .limit(pageable.next().getPageSize())
                .offset(pageable.next().getOffset())
                .build();
        return PageWrapperDTO.<T>builder()
                .data(page.getContent())
                .totalCount(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .pageNumber(pageable.getPageNumber())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .hasNextPage(page.hasNext())
                .next(next)
                .build();
    }

}
