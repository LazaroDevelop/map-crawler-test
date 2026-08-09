package net.space.developer.googlemapcrawler.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.space.developer.googlemapcrawler.dto.HtmlDesignDto;
import net.space.developer.googlemapcrawler.repository.HtmlDesignRepository;
import net.space.developer.googlemapcrawler.utils.Utils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class HtmlDesignService implements IHtmlDesignService{

    private final HtmlDesignRepository htmlDesignRepository;

    @Override
    public Mono<HtmlDesignDto> save(HtmlDesignDto htmlDesignDto) {
        return htmlDesignRepository
                .save(Utils.toHtmlDesignEntity(htmlDesignDto))
                .map(Utils::toHtmlDesignDto);
    }

    @Override
    public Mono<HtmlDesignDto> update(String id, HtmlDesignDto htmlDesignDto) {
        return htmlDesignRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception(
                        String.format("Html Design not found with id %s", htmlDesignDto.getId()))))
                .map(data -> {
                    data.setUserId(htmlDesignDto.getUserId());
                    data.setName(htmlDesignDto.getName());
                    data.setDesign(htmlDesignDto.getDesign());
                    data.setHtmlTemplate(htmlDesignDto.getHtmlTemplate());
                    return data;
                })
                .flatMap(htmlDesignRepository::save)
                .map(Utils::toHtmlDesignDto);
    }

    @Override
    public Mono<HtmlDesignDto> findById(String id) {
        return htmlDesignRepository
                .findById(id)
                .map(Utils::toHtmlDesignDto);
    }

    @Override
    public Flux<HtmlDesignDto> findAll() {
        return htmlDesignRepository
                .findAll()
                .map(Utils::toHtmlDesignDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        return htmlDesignRepository.deleteById(id);
    }
}
