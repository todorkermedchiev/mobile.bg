package bg.sofia.uni.fmi.dp.mobile.cli.command.commands;

import bg.sofia.uni.fmi.dp.mobile.advertisement.AdvertisementRepository;
import bg.sofia.uni.fmi.dp.mobile.parser.Searcher;

import java.util.Scanner;

public class SearchCommand implements Command {
    private static final String COMMAND_NAME = "search";

    private final Searcher searcher;
    private final AdvertisementRepository repository;

    public SearchCommand(Searcher searcher, AdvertisementRepository repository) {
        this.searcher = searcher;
        this.repository = repository;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter search query: ");
        String query = scanner.nextLine();

        var results = searcher.search(repository.findAll(), query);
        results.forEach(System.out::println);
    }

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public String getDescription() {
        return "Search for advertisements";
    }
}

