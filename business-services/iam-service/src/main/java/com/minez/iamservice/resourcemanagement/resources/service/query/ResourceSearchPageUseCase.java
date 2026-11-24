package com.minez.iamservice.resourcemanagement.resources.service.query;

import com.minez.corestarter.template.Query;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceSearch;
import com.minez.iamservice.resourcemanagement.resources.repository.ResourceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourceSearchPageUseCase extends Query<ResourceSearch, Page<Resource>> {
    ResourceRepository resources;

    @Override
    public Result<Page<Resource>> handle(ResourceSearch query) {
        PageRequest pageRequest = query.pageRequest();
        Page<Resource> found = resources.findAll(query, pageRequest);
        return Result.page(found);
    }
}
