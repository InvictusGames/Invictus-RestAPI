package cc.invictusgames.invictusresapi.mongo;

import cc.invictusgames.invictusresapi.InvictusRestAPI;
import cc.invictusgames.invictusresapi.util.configuration.defaults.MongoConfig;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.Document;

import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class MongoService {

    public static final ReplaceOptions REPLACE_OPTIONS = new ReplaceOptions().upsert(true);

    private final InvictusRestAPI api;
    private MongoClient client;

    // Invictus

    private MongoDatabase invictusDatabase;
    private MongoDatabase forumsDatabase;

    private MongoCollection<Document> ranks;
    private MongoCollection<Document> tags;
    private MongoCollection<Document> punishments;
    private MongoCollection<Document> profiles;
    private MongoCollection<Document> grants;
    private MongoCollection<Document> notes;
    private MongoCollection<Document> disguiseData;
    private MongoCollection<Document> discordData;
    private MongoCollection<Document> banphrases;

    private MongoCollection<Document> forumAccounts;
    private MongoCollection<Document> forumCategories;
    private MongoCollection<Document> forumForums;
    private MongoCollection<Document> forumThreads;
    private MongoCollection<Document> forumTickets;
    private MongoCollection<Document> forumTrophies;

    public boolean connect() {
        MongoConfig config = api.getMainConfig().getMongoConfig();
        if (config.isAuthEnabled()) {
            MongoCredential credential = MongoCredential.createCredential(
                    config.getAuthUsername(),
                    config.getAuthDatabase(),
                    config.getAuthPassword().toCharArray()
            );

            client = new MongoClient(
                    new ServerAddress(config.getHost(), config.getPort()),
                    Collections.singletonList(credential)
            );
        } else client = new MongoClient(config.getHost(), config.getPort());

        try {
            invictusDatabase = client.getDatabase("invictus");
            ranks = invictusDatabase.getCollection("ranks");
            tags = invictusDatabase.getCollection("tags");
            punishments = invictusDatabase.getCollection("punishments");
            profiles = invictusDatabase.getCollection("profiles");
            grants = invictusDatabase.getCollection("grants");
            notes = invictusDatabase.getCollection("notes");
            disguiseData = invictusDatabase.getCollection("disguiseData");
            discordData = invictusDatabase.getCollection("discordData");
            banphrases = invictusDatabase.getCollection("banphrases");

            forumsDatabase = client.getDatabase("forums");
            forumAccounts = forumsDatabase.getCollection("accounts");
            forumCategories = forumsDatabase.getCollection("categories");
            forumForums = forumsDatabase.getCollection("forums");
            forumThreads = forumsDatabase.getCollection("threads");
            forumTickets = forumsDatabase.getCollection("tickets");
            forumTrophies = forumsDatabase.getCollection("trophies");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}
