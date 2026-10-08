package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", User.Color.RED),
            new User(6, "pashka", "Pavel Mavrin", User.Color.BLUE),
            new User(9, "geranazavr555", "Georgiy Nazarov", User.Color.GREEN),
            new User(11, "tourist", "Gennady Korotkevich", User.Color.GREEN)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Codeforces Round #510 (Div. 2)",
                    "Hello, Codeforces. " +
                            "Codeforces Round #510 (Div. 2) will start at Monday, September 17, 2018 at 11:05. The round will be " +
                            "rated for Div. 2 contestants (participants with the rating below 2100). Div. 1 participants can take " +
                            "a part out of competition as usual. " +
                            "This round is held on the tasks of the school stage All-Russian Olympiad of Informatics 2018/2019 " +
                            "year in city Saratov. The problems were prepared by PikMike, fcspartakm, Ne0n25, BledDest, Ajosteen " +
                            "and Vovuh. Great thanks to our coordinator _kun_ for the help with the round preparation! I also" +
                            "would like to thank our testers DavidDenisov, PrianishnikovaRina, Decibit and Vshining. " +
                            "UPD: The scoring distribution is 500-1000-1500-2000-2250-2750.</p>", 9),
            new Post(2, "Lorem",
                    "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Alias debitis dolore dolorum facere " +
                            "hic nisi perferendis rerum sed. Accusamus, delectus ducimus facere fuga illum quaerat " +
                            "ratione voluptatibus! Autem cum, cupiditate dolor dolore, earum excepturi, illum iure " +
                            "molestias nostrum odio perferendis porro quasi qui quo recusandae repellendus similique " +
                            "sint vel. Ab accusantium adipisci debitis distinctio dolorum, eligendi error est fugit " +
                            "inventore labore laudantium nam nesciunt nihil nobis non nulla odio praesentium quae quia " +
                            "quibusdam quidem quod rem repudiandae saepe, sit totam vel voluptatem. A ab ad adipisci " +
                            "amet beatae cupiditate dignissimos dolorum est et eum, ex facere facilis illo maiores " +
                            "maxime natus necessitatibus nemo non officia officiis quidem ratione reiciendis rem " +
                            "soluta vitae? Ad aliquam amet deserunt ea eligendi est id, in ipsa iste necessitatibus " +
                            "nostrum optio pariatur quis. Ad aperiam assumenda atque aut doloribus ducimus enim ipsam " +
                            "molestias ratione repellat reprehenderit, voluptate? Accusamus amet blanditiis consequatur " +
                            "cumque dolore dolorem dolores eum expedita, explicabo facilis incidunt inventore iste iure " +
                            "iusto, magnam maxime necessitatibus nemo non nostrum, nulla numquam officiis pariatur quia " +
                            "ratione totam ut velit. Assumenda consequatur eaque excepturi magni optio quaerat quisquam, " +
                            "vel voluptatibus. Accusamus ad doloremque, ea, eum harum modi molestiae molestias nam neque " +
                            "nulla odio officiis quaerat quis reiciendis repellendus similique sint, tempora! Asperiores " +
                            "esse est hic in, necessitatibus placeat quidem reiciendis. Accusamus animi aut culpa cum " +
                            "doloremque ea eos ex facere fugit in laborum, minima molestiae necessitatibus, nesciunt " +
                            "nihil numquam obcaecati optio praesentium quaerat quam quasi quo reiciendis rem tempore " +
                            "tenetur unde voluptas voluptate. Dolor iure iusto nemo totam! Amet culpa dignissimos enim " +
                            "ex fugit impedit, ipsa ipsum iste itaque laudantium necessitatibus omnis perferendis quasi ", 1),
            new Post(3, "Information",
                    "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Asperiores enim expedita " +
                            "facilis fugiat ipsum iste nobis reprehenderit tempore ut voluptatibus?", 11),
            new Post(4, "Welcome to Codeforces",
                    "Hello, Codeforces! This is first post on the platform. " +
                            "We're glad to see you here.", 6)

    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
