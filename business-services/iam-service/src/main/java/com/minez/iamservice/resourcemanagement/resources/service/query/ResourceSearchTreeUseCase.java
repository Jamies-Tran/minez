package com.minez.iamservice.resourcemanagement.resources.service.query;

import com.minez.corestarter.template.Query;
import com.minez.corestarter.template.Result;
import com.minez.iamservice.resourcemanagement.resources.domain.Resource;
import com.minez.iamservice.resourcemanagement.resources.domain.ResourceTree;
import com.minez.iamservice.resourcemanagement.resources.domain.argument.ResourceSearch;
import com.minez.iamservice.resourcemanagement.resources.repository.ResourceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourceSearchTreeUseCase extends Query<ResourceSearch, Page<ResourceTree>> {
    ResourceRepository resources;

    @Override
    public Result<Page<ResourceTree>> handle(ResourceSearch query) {
        PageRequest pageRequest = query.pageRequest();
        Page<Resource> page = resources.findTopTree(query, pageRequest);
        Page<ResourceTree> tree = computeTree(page);
        return Result.page(tree);
    }

    private Page<ResourceTree> computeTree(Page<Resource> page) {
        if(page.isEmpty()) {
            return Page.empty();
        }
        List<UUID> resourceUuids = page.map(Resource::resourceUuid).getContent();
        List<Resource> children = resources.findAllByParentUuidIn(resourceUuids);
        Map<UUID, List<ResourceTree>> childrenMap = computeChildrenMap(children);
        return page.map(r -> ResourceTree.apply(r, childrenMap.getOrDefault(r.resourceUuid(), List.of())));
    }

    private Map<UUID, List<ResourceTree>> computeChildrenMap(List<Resource> children) {
        if (children.isEmpty()) {
            return Collections.emptyMap();
        }
        List<ResourceTree> childrenTree = children.stream()
                .map(ResourceTree::apply)
                .toList();
        Map<UUID, ResourceTree> childrenMap = childrenTree.stream()
                .collect(Collectors.toMap(ResourceTree::resourceUuid, Function.identity()));
        List<ResourceTree> node = new ArrayList<>();
        for (ResourceTree resource : childrenTree) {
            ResourceTree parent = childrenMap.get(resource.parentUuid());
            if(Objects.nonNull(parent)) {
                parent.children().add(resource);
            } else {
                node.add(resource);
            }
        }
        return node.stream()
                .collect(Collectors.groupingBy(ResourceTree::parentUuid, Collectors.toList()));
    }
}
