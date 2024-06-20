package yuval.pinhas.bookwormsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A fragment representing the Library page of the Bookworms app.
 * It displays a collection of book covers that users can click on to view book details, write comment, see others comments.
 *
 * @author Yuval Pinhas
 */
public class LibraryFragment extends Fragment {

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    /**
     * Called immediately after onCreateView(LayoutInflater, ViewGroup, Bundle) has returned,
     * but before any saved state has been restored in to the view.
     *
     * @param view               The View returned by onCreateView(LayoutInflater, ViewGroup, Bundle).
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView book1 = view.findViewById(R.id.book1);
        book1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("The Between", 1,
                        "Seventeen-year-old Ana Moon is having a rough week. It starts with a fight after school, then suspension, followed by mandatory psych visits. Still, Ana " +
                        "hopes therapy will help her with another problem--the disturbing feeling that someone, or something, is following her.\n\nThen, during a shocking train crash," +
                        " life goes from bad to bizarre. In the space of mere seconds, Ana's best friend is gone—taken right in front of her eyes by an incredible, terrifying beast.\n\n" +
                        "Seeking answers, Ana joins forces with the mysterious Malik and his covert clan to find her friend and return home. But there's a larger war under way, and " +
                        "unimaginable evil lurks in the shadows. If they hope to make it home, Ana and her friends must gather the strength to fight—or face the collapse of the universe as they know it.");
            }
        });

        ImageView book2 = view.findViewById(R.id.book2);
        book2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("A Calling for Charlie Barnes", 2,
                        "Someone is telling the story of the life of Charlie Barnes, and it doesn't appear to be going well. Too " +
                        "often divorced, discontent with life's compromises and in a house he hates, this lifelong schemer and eternal romantic would like out of his present circumstances and " +
                        "into the American dream. But when the twin calamities of the Great Recession and a cancer scare come along to compound his troubles, his dreams dwindle further, and an " +
                        "infinite past full of forking paths quickly tapers to a black dot.\n\nThen, against all odds, something goes right for a change: Charlie is granted a second act. With " +
                        "help from his storyteller son, he surveys the facts of his life and finds his true calling where he least expects it—in a sacrifice that redounds with selflessness and " +
                        "love—at last becoming the man his son always knew he could be.\n\nA Calling for Charlie Barnes is a profound and tender portrait of a man whose desperate need to be loved " +
                        "is his downfall, and a brutally funny account of how that love is ultimately earned.");
            }
        });

        ImageView book3 = view.findViewById(R.id.book3);
        book3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("The Neighborhood", 3,
                        "One day Enrique, a high-profile businessman, receives a visit from Rolando Garro, the editor of a notorious magazine that specializes in salacious exposes. " +
                                "Garro presents Enrique with lewd pictures from an old business trip and demands that he invest in the magazine. Enrique refuses, and the next day the pictures are" +
                                " on the front page. Meanwhile, Enrique's wife is in the midst of a passionate and secret affair with the wife of Enrique's lawyer and best friend. When Garro shows " +
                                "up murdered, the two couples are thrown into a whirlwind of navigating Peru's unspoken laws and customs, while the staff of the magazine embark on their greatest " +
                                "expose yet.\n\nIronic and sensual, provocative and redemptive, the novel swirls into the kind of restless realism that has become Mario Vargas Llosa's signature " +
                                "style. A twisting, unpredictable tale, The Neighborhood is at once a scathing indictment of Fujimori's regime and a crime thriller that evokes the vulgarity of " +
                                "freedom in a corrupt system.");
            }
        });

        ImageView book4 = view.findViewById(R.id.book4);
        book4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("Great Circle", 4,
                        "After being rescued as infants from a sinking ocean liner in 1914, Marian and Jamie Graves are raised by their dissolute uncle in Missoula, Montana. There—after" +
                                " encountering a pair of barnstorming pilots passing through town in beat-up biplanes—Marian commences her lifelong love affair with flight. At fourteen she drops out " +
                                "of school and finds an unexpected and dangerous patron in a wealthy bootlegger who provides a plane and subsidizes her lessons, an arrangement that will haunt her for " +
                                "the rest of her life, even as it allows her to fulfill her destiny: circumnavigating the globe by flying over the North and South Poles.\n\nA century later, Hadley " +
                                "Baxter is cast to play Marian in a film that centers on Marian's disappearance in Antarctica. Vibrant, canny, disgusted with the claustrophobia of Hollywood, Hadley " +
                                "is eager to redefine herself after a romantic film franchise has imprisoned her in the grip of cult celebrity. Her immersion into the character of Marian unfolds, " +
                                "thrillingly, alongside Marian's own story, as the two women's fates—and their hunger for self-determination in vastly different geographies and times—collide. " +
                                "Epic and emotional, meticulously researched and gloriously told, Great Circle is a monumental work of art, and a tremendous leap forward for the prodigiously gifted " +
                                "Maggie Shipstead.");
            }
        });

        ImageView book5 = view.findViewById(R.id.book5);
        book5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("The Rain Heron", 5,
                        "Ren lives alone on the remote frontier of a country devastated by a coup. High on the forested slopes, she survives by hunting and trading—and forgetting.\n\n" +
                                "But when a young soldier comes to the mountains in search of a local myth, Ren is inexorably drawn into her impossible mission. As their lives entwine, unravel and" +
                                " erupt—as myths merge with reality—both Ren and the soldier are forced to confront what they regret, what they love, and what they fear.\n\n The Rain Heron is the " +
                                "dizzying, dazzling new novel from the author of Flames.");
            }
        });

        ImageView book6 = view.findViewById(R.id.book6);
        book6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("Clear", 6,
                        "John, an impoverished Scottish minister, has accepted a job evicting the lone remaining occupant of an island north of Scotland—Ivar, " +
                        "who has been living alone for decades, with only the animals and the sea for company. Though his wife, Mary, has serious misgivings about the errand, he decides to go anyway" +
                        ", setting in motion a chain of events that neither he nor Mary could have predicted.\n\nShortly after John reaches the island, he falls down a cliff and is found, unconscious " +
                        "and badly injured, by Ivar who takes him home and tends to his wounds. The two men do not speak a common language, but as John builds a dictionary of Ivar’s world, they learn " +
                        "to communicate and, as Ivar sees himself for the first time in decades reflected through the eyes of another person, they build a fragile, unusual connection.\n\nUnfolding in the" +
                        " 1840s in the final stages of the infamous Scottish Clearances—which saw whole communities of the rural poor driven off the land in a relentless program of forced evictions—this" +
                        " singular, beautiful, deeply surprising novel explores the differences and connections between us, the way history shapes our deepest convictions, and how the human spirit can " +
                        "survive despite all odds. Moving and unpredictable, sensitive and spellbinding, Clear is a profound and pleasurable read.");
            }
        });

        ImageView book7 = view.findViewById(R.id.book7);
        book7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("One of Us Knows", 7,
                        "Years after a breakdown and a diagnosis of dissociative identity disorder derailed her historical preservationist career, Kenetria Nash and her alters have been given a " +
                                "second chance they can’t refuse: a position as resident caretaker of a historic home. Having been dormant for years, Ken has no idea what led them to this isolated Hudson " +
                                "River island, but she’s determined not to ruin their opportunity.\n\nThen a surprise visit from the home’s conservation trust just as a Nor’easter bears down on the island " +
                                "disrupts her newfound life, leaving Ken trapped with a group of possibly dangerous strangers—including the man who brought her life tumbling down years earlier. When he turns " +
                                "up dead, Ken is the prime suspect.\n\nCaught in a web of secrets and in a race against time, Ken and her alters must band together to prove their innocence and discover the truth " +
                                "of Kavanaugh Island—and their own past—or they risk losing not only their future, but their life.");
            }
        });

        ImageView book8 = view.findViewById(R.id.book8);
        book8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("Land of Tornadoes", 8,
                        "The dust bowl has returned, Frackheads roam the lawless streets, and one unusual family with eight adopted teens plots to hack the oil company that caused it all and ruined " +
                                "their father's reputation, but the harsh environment, struggling relationships, and family secrets that come to light threaten Family dynamics, teens, hacking, rich vs. poor, anti-" +
                                "capitalism, conservation, climate, science fiction");
            }
        });

        ImageView book9 = view.findViewById(R.id.book9);
        book9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("Get In Trouble", 9,
                        "She has been hailed by Michael Chabon as “the most darkly playful voice in American fiction” and by Neil Gaiman as “a national treasure.” Now Kelly Link’s eagerly awaited new " +
                                "collection--her first for adult readers in a decade--proves indelibly that this bewitchingly original writer is among the finest we have.\n\nLink has won an ardent following for " +
                                "her ability to take readers deep into an unforgettable, brilliantly constructed fictional universe with each new story. In “The Summer People,” a young girl in rural North Carolina " +
                                "serves as uneasy caretaker to the mysterious, never-quite-glimpsed visitors who inhabit the cottage behind her house. In “I Can See Right Through You,” a middle-aged movie star makes " +
                                "a disturbing trip to the Florida swamp where his former on- and off-screen love interest is shooting a ghost-hunting reality show. In “The New Boyfriend,” a suburban slumber party takes " +
                                "an unusual turn, and a teenage friendship is tested, when the spoiled birthday girl opens her big present: a life-size animated doll.\n\nHurricanes, astronauts, evil twins, bootleggers, " +
                                "Ouija boards, iguanas, The Wizard of Oz, superheroes, the Pyramids...These are just some of the talismans of an imagination as capacious and as full of wonder as that of any writer today. " +
                                "But as fantastical as these stories can be, they are always grounded in sly humor and an innate generosity of feeling for the frailty--and the hidden strengths--of human beings. In Get in " +
                                "Trouble, this one-of-a-kind talent expands the boundaries of what short fiction can do.");
            }
        });

        ImageView book10 = view.findViewById(R.id.book10);
        book10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("Edge Case", 10,
                        "When her husband suddenly disappears, a young woman must uncover where he went—and who she might be without him—in this striking debut of immigration, identity, and marriage.\n\n After " +
                                "another taxing day as the sole female employee at her New York City tech startup, Edwina comes home to find that her husband, Marlin, has packed up a suitcase and left. The only question " +
                                "now is why. Did he give up on their increasingly hopeless quest to secure their green cards and decide to return to Malaysia? Was it the death of his father that sent him into a tailspin? " +
                                "Or has his strange, sudden change in personality finally made Marlin and Edwina strangers to each other?\n\nAs Edwina searches the city for traces of her husband, she simultaneously sifts " +
                                "through memories of their relationship, hoping to discover the moment when something went wrong. All the while, a coworker is making increasingly uncomfortable advances toward her. And she " +
                                "can’t hide the truth about Marlin’s disappearance from her overbearing, eccentric mother for much longer. Soon Edwina will have to decide how much she is willing to sacrifice in order to stay " +
                                "in her marriage and in America.\n\nPoignant and darkly funny, Edge Case is a searing meditation on intimacy, estrangement, and the fractured nature of identity. In this moving debut, YZ " +
                                "Chin explores the imperfect yet enduring relationships we hold to country and family.");
            }
        });

        ImageView book11 = view.findViewById(R.id.book11);
        book11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("V for Vendetta", 11,
                        "A frightening and powerful tale of the loss of freedom and identity in a chillingly believable totalitarian world, V for Vendetta stands as one of the highest achievements of the comics " +
                                "medium and a defining work for creators Alan Moore and David Lloyd.\n\nSet in an imagined future England that has given itself over to fascism, this groundbreaking story captures both the " +
                                "suffocating nature of life in an authoritarian police state and the redemptive power of the human spirit which rebels against it. Crafted with sterling clarity and intelligence, V for " +
                                "Vendetta brings an unequaled depth of characterization and verisimilitude to its unflinching account of oppression and resistance.");
            }
        });

        ImageView book12 = view.findViewById(R.id.book12);
        book12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("The Martian", 12,
                        "Six days ago, astronaut Mark Watney became one of the first people to walk on Mars.\n\nNow, he’s sure he’ll be the first person to die there.\n\nAfter a dust storm nearly kills him and " +
                                "forces his crew to evacuate while thinking him dead, Mark finds himself stranded and completely alone with no way to even signal Earth that he’s alive—and even if he could get word out, his " +
                                "supplies would be gone long before a rescue could arrive.\n\nChances are, though, he won’t have time to starve to death. The damaged machinery, unforgiving environment, or plain-old “human " +
                                "error” are much more likely to kill him first.\n\nBut Mark isn’t ready to give up yet. Drawing on his ingenuity, his engineering skills — and a relentless, dogged refusal to quit — he " +
                                "steadfastly confronts one seemingly insurmountable obstacle after the next. Will his resourcefulness be enough to overcome the impossible odds against him?");
            }
        });

        ImageView book13 = view.findViewById(R.id.book13);
        book13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("Ready Player One", 13,
                        "IN THE YEAR 2044, reality is an ugly place. The only time teenage Wade Watts really feels alive is when he's jacked into the virtual utopia known as the OASIS. Wade's devoted his life " +
                                "to studying the puzzles hidden within this world's digital confines, puzzles that are based on their creator's obsession with the pop culture of decades past and that promise massive power " +
                                "and fortune to whoever can unlock them.\n\nBut when Wade stumbles upon the first clue, he finds himself beset by players willing to kill to take this ultimate prize. The race is on, and if " +
                                "Wade's going to survive, he'll have to win—and confront the real world he's always been so desperate to escape.");
            }
        });

        ImageView book14 = view.findViewById(R.id.book14);
        book14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("Weyward", 14,
                        "2019: Under cover of darkness, Kate flees London for ramshackle Weyward Cottage, inherited from a great aunt she barely remembers. With its tumbling ivy and overgrown garden, the cottage is" +
                                " worlds away from the abusive partner who tormented Kate. But she begins to suspect that her great aunt had a secret. One that lurks in the bones of the cottage, hidden ever since the witch-hunts" +
                                " of the 17th century.\n\n1619: Altha is awaiting trial for the murder of a local farmer who was stampeded to death by his herd. As a girl, Altha’s mother taught her their magic, a kind not rooted " +
                                "in spell casting but in a deep knowledge of the natural world. But unusual women have always been deemed dangerous, and as the evidence for witchcraft is set out against Altha, she knows it will " +
                                "take all of her powers to maintain her freedom.\n\n1942: As World War II rages, Violet is trapped in her family's grand, crumbling estate. Straitjacketed by societal convention, she longs for the" +
                                " robust education her brother receives––and for her mother, long deceased, who was rumored to have gone mad before her death. The only traces Violet has of her are a locket bearing the initial W " +
                                "and the word weyward scratched into the baseboard of her bedroom.\n\nWeaving together the stories of three extraordinary women across five centuries, Emilia Hart's Weyward is an enthralling novel" +
                                " of female resilience and the transformative power of the natural world.");
            }
        });

        ImageView book15 = view.findViewById(R.id.book15);
        book15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("Holly", 15,
                        "Stephen King’s Holly marks the triumphant return of beloved King character Holly Gibney. Readers have witnessed Holly’s gradual transformation from a shy (but also brave and ethical) recluse in " +
                                "Mr. Mercedes to Bill Hodges’s partner in Finders Keepers to a full-fledged, smart, and occasionally tough private detective in The Outsider. In King’s new novel, Holly is on her own, and up against " +
                                "a pair of unimaginably depraved and brilliantly disguised adversaries.\n\nWhen Penny Dahl calls the Finders Keepers detective agency hoping for help locating her missing daughter, Holly is reluctant" +
                                " to accept the case. Her partner, Pete, has Covid. Her (very complicated) mother has just died. And Holly is meant to be on leave. But something in Penny Dahl’s desperate voice makes it impossible for" +
                                " Holly to turn her down.\n\nMere blocks from where Bonnie Dahl disappeared live Professors Rodney and Emily Harris. They are the picture of bourgeois respectability: married octogenarians, devoted to " +
                                "each other, and semi-retired lifelong academics. But they are harboring an unholy secret in the basement of their well-kept, book-lined home, one that may be related to Bonnie’s disappearance. And it" +
                                " will prove nearly impossible to discover what they are up to: they are savvy, they are patient, and they are ruthless.\n\nHolly must summon all her formidable talents to outthink and outmaneuver " +
                                "the shockingly twisted professors in this chilling new masterwork from Stephen King.");
            }
        });

        ImageView book16 = view.findViewById(R.id.book16);
        book16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookActivity("King: A Life", 16,
                        "The first full biography in decades, King mixes revelatory and exhaustive new research with brisk and accessible storytelling to forge the definitive life for our times.\n\nVividly written and " +
                                "exhaustively researched, Jonathan Eig’s A Life is the first major biography in decades of the civil rights icon Martin Luther King Jr.―and the first to include recently declassified FBI files. In " +
                                "this revelatory new portrait of the preacher and activist who shook the world, the bestselling biographer gives us an intimate view of the courageous and often emotionally troubled human being who " +
                                "demanded peaceful protest for his movement but was rarely at peace with himself. He casts fresh light on the King family’s origins as well as MLK’s complex relationships with his wife, father, " +
                                "and fellow activists. King reveals a minister wrestling with his own human frailties and dark moods, a citizen hunted by his own government, and a man determined to fight for justice even if it " +
                                "proved to be a fight to the death. As he follows MLK from the classroom to the pulpit to the streets of Birmingham, Selma, and Memphis, Eig dramatically re-creates the journey of a man who recast" +
                                " American race relations and became our only modern-day founding father―as well as the nation’s most mourned martyr.\n\nIn this landmark biography, Eig gives us an MLK for our a deep thinker, a " +
                                "brilliant strategist, and a committed radical who led one of history’s greatest movements, and whose demands for racial and economic justice remain as urgent today as they were in his lifetime.\n\n" +
                                "Includes 8 pages of black-and-white photographs");
            }
        });
    }


    /**
     * Opens the BookActivity to display details of the selected book.
     *
     * @param bookName    The name of the selected book.
     * @param bookNumber  The number of the selected book.
     * @param bookSummary The summary of the selected book.
     */
    private void showBookActivity(String bookName, int bookNumber, String bookSummary) {
            // Create an intent to start the BookActivity
            Intent intent = new Intent(getActivity(), BookActivity.class);

            // Pass book details as extras to the intent
            intent.putExtra("bookName", bookName);
            intent.putExtra("bookSummary", bookSummary);
            intent.putExtra("bookNumber", bookNumber);

            startActivity(intent);
    }
}

