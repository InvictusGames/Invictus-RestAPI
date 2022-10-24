package cc.invictusgames.invictusresapi.forum;

import cc.invictusgames.invictusresapi.InvictusRestAPI;
import cc.invictusgames.invictusresapi.forum.account.AccountService;
import cc.invictusgames.invictusresapi.forum.category.CategoryService;
import cc.invictusgames.invictusresapi.forum.forum.ForumModelService;
import cc.invictusgames.invictusresapi.forum.thread.ThreadService;
import cc.invictusgames.invictusresapi.forum.ticket.TicketService;
import cc.invictusgames.invictusresapi.forum.trophy.TrophyService;
import lombok.Getter;

@Getter
public class ForumService {

    private final InvictusRestAPI api;

    private final AccountService accountService;
    private final CategoryService categoryService;
    private final ForumModelService forumModelService;
    private final ThreadService threadService;
    private final TicketService ticketService;
    private final TrophyService trophyService;

    public ForumService(InvictusRestAPI api) {
        this.api = api;
        this.accountService = new AccountService(api);

        this.categoryService = new CategoryService(api);
        categoryService.loadCategories();

        this.forumModelService = new ForumModelService(api);
        forumModelService.loadForums();

        this.threadService = new ThreadService(api);
        this.ticketService = new TicketService(api);

        this.trophyService = new TrophyService(api);
        trophyService.loadTrophies();
    }
}
