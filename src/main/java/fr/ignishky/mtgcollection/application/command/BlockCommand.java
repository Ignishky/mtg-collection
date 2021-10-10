package fr.ignishky.mtgcollection.application.command;

import com.linecorp.armeria.server.annotation.Put;
import fr.ignishky.mtgcollection.application.AnnotatedService;
import fr.ignishky.mtgcollection.domain.block.service.BlockLoader;
import org.springframework.stereotype.Controller;

@Controller
public class BlockCommand implements AnnotatedService {

    private final BlockLoader blockLoader;

    public BlockCommand(BlockLoader blockLoader) {
        this.blockLoader = blockLoader;
    }

    @Put("/blocks")
    public void loadAll() {
        blockLoader.load();
    }
}
