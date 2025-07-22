package net.samitkumar.ecom.catalog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.samitkumar.ecom.catalog.entity.Product;
import net.samitkumar.ecom.catalog.repository.ProductRepository;
import net.samitkumar.ecom.catalog.utility.BadRequestException;
import net.samitkumar.ecom.catalog.utility.CategoryNotFoundException;
import net.samitkumar.ecom.catalog.entity.Category;
import net.samitkumar.ecom.catalog.repository.CategoryRepository;
import net.samitkumar.ecom.catalog.utility.ProductNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogController {
    final CategoryRepository categoryRepository;
    final ProductRepository productRepository;

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions
                .route()
                .GET("/products", this::fetchAllProducts)
                .path("/category", categoryBuilder -> categoryBuilder
                        .path("/{id}/product", productBuilder -> productBuilder
                                .GET("", this::allProductByCategoryId)
                                .POST("", this::createProductByCategoryId)
                                .GET("/{productId}", this::productByCategoryIdAndProductId)
                                .PUT("/{productId}", this::updateProductByCategoryId)
                                .PATCH("/{productId}", this::patchProductByCategoryId)
                                .DELETE("/{productId}", this::deleteProductByCategoryId)
                        )
                        .GET("", this::getAllCategories)
                        .POST("", this::createCategory)
                        .GET("/{id}", this::getCategoryById)
                        .PUT("/{id}", this::updateCategory)
                        .PATCH("/{id}", this::patchCategory)
                        .DELETE("/{id}", this::deleteCategory)
                )
                .build();
    }

    private Mono<ServerResponse> fetchAllProducts(ServerRequest request) {
        log.info("##fetchAllProducts##");
        return Mono.fromCallable(productRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private Mono<ServerResponse> patchProductByCategoryId(ServerRequest request) {
        var categoryId = Long.valueOf(request.pathVariable("id"));
        var productId = Long.valueOf(request.pathVariable("productId"));
        log.info("##patchProductByCategoryId## categoryId: {}, productId: {}", categoryId, productId);
        return Mono.fromCallable(() -> productRepository.findProductByCategoryAndId(categoryId, productId).orElseThrow(ProductNotFoundException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .zipWith(request.bodyToMono(Product.class), (db, body) -> {
                    if (nonNull(body.getName())) {
                        db.setName(body.getName());
                    }
                    if (nonNull(body.getDescription())) {
                        db.setDescription(body.getDescription());
                    }
                    if (nonNull(body.getPrice())) {
                        db.setPrice(body.getPrice());
                    }
                    if (nonNull(body.getInventory())) {
                        db.setInventory(body.getInventory());
                    }
                    return db;
                })
                .map(productRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private Mono<ServerResponse> deleteProductByCategoryId(ServerRequest request) {
        var categoryId = Long.valueOf(request.pathVariable("id"));
        var productId = Long.valueOf(request.pathVariable("productId"));
        log.info("##deleteProductByCategoryId## categoryId: {}, productId: {}", categoryId, productId);
        return Mono.fromRunnable(() -> productRepository.deleteProductByCategoryAndId(categoryId, productId))
                .subscribeOn(Schedulers.boundedElastic())
                .then(ServerResponse.ok().build());
    }

    private Mono<ServerResponse> updateProductByCategoryId(ServerRequest request) {
        var categoryId = Long.valueOf(request.pathVariable("id"));
        var productId = Long.valueOf(request.pathVariable("productId"));
        log.info("##updateProductByCategoryId## categoryId: {}, productId: {}", categoryId, productId);
        return Mono.fromCallable(() -> productRepository.findProductByCategoryAndId(categoryId, productId).orElseThrow(ProductNotFoundException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .zipWith(request.bodyToMono(Product.class), (db, body) -> {
                    body.setCategory(db.getCategory());
                    body.setId(db.getId());
                    return body;
                })
                .map(productRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private Mono<ServerResponse> productByCategoryIdAndProductId(ServerRequest request) {
        var categoryId = Long.valueOf(request.pathVariable("id"));
        var productId = Long.valueOf(request.pathVariable("productId"));
        log.info("##productByCategoryIdAndProductId## categoryId: {}, productId: {}", categoryId, productId);
        return Mono.fromCallable(() -> productRepository.findProductByCategoryAndId(categoryId, productId).orElseThrow(ProductNotFoundException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private Mono<ServerResponse> createProductByCategoryId(ServerRequest request) {
        var categoryId = Long.valueOf(request.pathVariable("id"));
        log.info("##createProductByCategoryId## categoryId: {}", categoryId);
        return request.bodyToMono(Product.class)
                .map(product -> {
                    product.setCategory(categoryId);
                    return product;
                })
                .doOnNext(this::validateProduct)
                .map(productRepository::save)
                .map(product -> {
                    product.setCategory(categoryId);
                    return product;
                })
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private void validateProduct(Product product) {
        if(isNull(product.getName()) || isNull(product.getDescription()) || isNull(product.getPrice())) {
            throw new BadRequestException("Product name or description or price must not be null");
        }
    }

    private Mono<ServerResponse> allProductByCategoryId(ServerRequest request) {
        var categoryId = Long.valueOf(request.pathVariable("id"));
        log.info("##allProductByCategoryId## categoryId: {}", categoryId);
        return Mono.fromCallable(() -> productRepository.findByCategory(categoryId))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private Mono<ServerResponse> deleteCategory(ServerRequest request) {
        var id = Long.valueOf(request.pathVariable("id"));
        return Mono.fromRunnable(() -> categoryRepository.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .then(ServerResponse.ok().build());
    }

    private Mono<ServerResponse> patchCategory(ServerRequest request) {
        var id = Long.valueOf(request.pathVariable("id"));
        log.info("##patchCategory## id: {}", id);
        return Mono.fromCallable(() -> categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .zipWith(request.bodyToMono(Category.class), (db, body) -> {
                    if (nonNull(body.getName())) {
                        db.setName(body.getName());
                    }
                    if (nonNull(body.getDescription())) {
                        db.setDescription(body.getDescription());
                    }
                    if (nonNull(body.getProducts()) && !body.getProducts().isEmpty()) {
                        db.setProducts(body.getProducts());
                    }
                    return db;
                })
                .map(categoryRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private Mono<ServerResponse> updateCategory(ServerRequest request) {
        var id = Long.valueOf(request.pathVariable("id"));
        log.info("##updateCategory## id: {}", id);
        return Mono.fromCallable(() -> categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new))
                .subscribeOn(Schedulers.boundedElastic())
                .zipWith(request.bodyToMono(Category.class), (db, body) -> {
                    body.setId(db.getId());
                    return categoryRepository.save(body);
                })
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private Mono<ServerResponse> createCategory(ServerRequest request) {
        log.info("##createCategory##");
        return request
                .bodyToMono(Category.class)
                .doOnNext(this::validateCategory)
                .map(categoryRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private void validateCategory(Category category) {
        if (isNull(category.getName()) || isNull(category.getDescription())) {
            throw new BadRequestException("Category name or description must not be null");
        }
    }

    private Mono<ServerResponse> getCategoryById(ServerRequest request) {
        var id = Long.valueOf(request.pathVariable("id"));
        log.info("##getCategoryById## id: {}", id);
        return Mono.fromCallable(() -> categoryRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(category -> category
                        .map(ServerResponse.ok()::bodyValue)
                        .orElseGet(() -> ServerResponse.notFound().build()));
    }

    private Mono<ServerResponse> getAllCategories(ServerRequest request) {
        return Mono.fromCallable(categoryRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(ServerResponse.ok()::bodyValue);
    }
}
