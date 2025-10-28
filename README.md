Лабораториска вежба 1 - за групите со префикс А
Completion requirements
Спецификација за лабораториската вежба
1. Креирање на Spring Boot проект
Креирајте нов Spring Boot проект со следните карактеристики:

groupId: mk.ukim.finki.wp
artifactId: lab
Зависности: Истите како проектот од аудиториските вежби (видете ги <dependency> таговите во pom.xml)
2. Креирање на Model класи
Дефинирајте пакет mk.ukim.finki.wp.lab.model и креирајте ги следните класи:

2.1 BookReservation класа
Креирајте класа BookReservation која содржи:

String bookTitle
String readerName
String readerAddress
Long numberOfCopies
2.2 Book класа
Креирајте класа Book која содржи:

String title
String genre
double averageRating
3. Креирање на DataHolder класа
Креирајте класа DataHolder во пакетот mk.ukim.finki.wp.lab.bootstrap која ќе содржи:

Статична листа: public static List<Book> books = new ArrayList<>(); иницијализирана со 10 вредности
Статична листа: public static List<BookReservation> reservations = new ArrayList<>(); (иницијално празна)
4. Креирање на Repository слој
4.1 BookRepository интерфејс
Дефинирајте интерфејс BookRepository во пакетот mk.ukim.finki.wp.lab.repository:

public interface BookRepository {
    List<Book> findAll();
    List<Book> searchBooks(String text, Double rating);
}
4.2 InMemoryBookRepository имплементација
Креирајте класа InMemoryBookRepository која го имплементира BookRepository интерфејсот.

Имплементација:

findAll() - враќа ја листата од DataHolder.books
searchBooks(String text, Double rating) - пребарува низ листата на книги и ги враќа оние чии наслов го содржи текстот text и имаат рејтинг поголем или еднаков на rating.
4.3 BookReservationRepository интерфејс
Дефинирајте интерфејс BookReservationRepository во пакетот mk.ukim.finki.wp.lab.repository:

public interface BookReservationRepository {
    BookReservation save(BookReservation reservation);
}
4.4 InMemoryBookReservationRepository имплементација
Креирајте класа InMemoryBookReservationRepository која го имплементира BookReservationRepository интерфејсот.

Имплементација:

save(BookReservation reservation) - ја додава резервацијата во DataHolder.reservations листата и ја враќа зачуваната резервација
5. Креирање на Service слој
5.1 BookService интерфејс
Дефинирајте интерфејс BookService во пакетот mk.ukim.finki.wp.lab.service:

public interface BookService {
    List<Book> listAll();
    List<Book> searchBooks(String text, Double rating);
}
5.2 BookReservationService интерфејс
Дефинирајте интерфејс BookReservationService во пакетот mk.ukim.finki.wp.lab.service:

public interface BookReservationService {
    BookReservation placeReservation(String bookTitle, String readerName, String readerAddress, int numberOfCopies);
}
5.3 Имплементација на сервисите
Имплементирајте ги сервисите (креирајте BookServiceImpl и BookReservationServiceImpl класи). BookService треба да зависи од BookRepository, а BookReservationService треба да зависи од BookReservationRepository.

6. Креирање на Web слој (Servlets)
6.1 BookListServlet
Креирајте сервлет BookListServlet во пакетот mk.ukim.finki.wp.lab.web и мапирајте го на патеката /. Овој сервлет треба да зависи од BookService и да ги прикаже сите добиени книги од методот listAll(). Овозможете корисникот да избере една од книгите и за истата да наведе број на копии што сака да ги резервира. Креирајте по едно радио копче за секоја книга каде што вредноста на копчето ќе биде насловот на книгата, а текстот кој ќе се прикаже ќе биде во форматот: Title: <book_title>, Genre: <book_genre>, Rating: <average_rating>

Прилагодете го фајлот listBooks.html за изгледот на оваа страница.

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Book Reservation - Welcome and choose a Book</title>
    <style type="text/css">
        body {
            width: 800px;
            margin: auto;
            font-family: Arial, sans-serif;
        }
        h1 {
            color: #333;
        }
        form {
            margin-top: 20px;
        }
        .book-option {
            margin: 10px 0;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .book-option:hover {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>
    <header>
        <h1>Welcome to our Book Reservation App</h1>
    </header>
    <main>
        <form action="/bookReservation" method="POST">
            <h2>Choose a book:</h2>
            <!-- Display radio buttons for each book,
                 the value should be the book title 
                 and the displayed text should be:
                 Title: <book_title>, Genre: <book_genre>, Rating: <average_rating> -->

            <h2>Enter your information:</h2>
            <label>Your Name:</label>
            <input type="text" name="readerName" required><br/>
            <label>Your Address:</label>
            <input type="text" name="readerAddress" required><br/>

            <h2>Choose number of copies:</h2>
            <input type="number" name="numCopies" min="1" max="10" required><br/>
            <br/>
            <input type="submit" value="Reserve Book">
        </form>
    </main>
</body>
</html>
6.2 BookReservationServlet
При избор на книга, треба да ја прикажете резервацијата на корисникот. За оваа цел креирајте сервлет BookReservationServlet мапиран на /bookReservation. Овој сервлет треба да зависи од BookReservationService.

Сервлетот треба да:

Ги прочита податоците испратени од формата (избраната книга, број на копии, име и адреса на читателот)
Да ја земе IP адресата на клиентот
Креира резервација со повикување на методот placeReservation() од сервисот
Ја прикаже страната за потврда со деталите за резервацијата
Во фолдерот src/main/resources/templates додадете фајл reservationConfirmation.html и прилагодете го за изгледот на страната за потврда. Динамички пополнете ги податоците во табелата.

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Book Reservation - Confirmation</title>
    <style type="text/css">
        body {
            width: 800px;
            margin: auto;
            font-family: Arial, sans-serif;
        }
        h1 {
            color: #333;
        }
        table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }
        table, td, th {
            border: 1px solid #333;
        }
        th {
            background-color: #4CAF50;
            color: white;
            padding: 12px;
        }
        td {
            padding: 10px;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <section>
        <header>
            <h1>Book Reservation - Confirmation</h1>
        </header>
        <table>
            <tr>
                <th colspan="2">Your Reservation Status</th>
            </tr>
            <tr>
                <td><b>Reader Name</b></td>
                <td><!-- Display reader name from servlet --></td>
            </tr>
            <tr>
                <td><b>Client IP Address</b></td>
                <td><!-- Display IP address from servlet --></td>
            </tr>
            <tr>
                <td><b>Reservation for Book</b></td>
                <td><!-- Display book title from servlet --></td>
            </tr>
            <tr>
                <td><b>Number of copies</b></td>
                <td><!-- Display number of copies from servlet --></td>
            </tr>
        </table>
    </section>
</body>
</html>
7. Имплементација на пребарување
Да се имплементира можност за пребарување на книгите на почетната страна listBooks.html. Треба да се прикажат само книгите кои ги исполнуваат условите од пребарувањето. Пребарувањето треба да се изврши според два параметри:

книги кои го содржат текстот испратен од страна на корисникот во нивниот наслов
книги кои имаат рејтинг поголем или еднаков на внесената вредност од страна на корисникот

8. da se dodade infoto za knigata na drugata strana
9.  genre i rating da dodades
