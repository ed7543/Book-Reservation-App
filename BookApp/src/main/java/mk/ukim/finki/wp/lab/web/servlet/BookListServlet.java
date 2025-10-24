package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.service.BookReservationService;
import mk.ukim.finki.wp.lab.service.BookService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "BookListServlet", urlPatterns = "/servlet/books")
public class BookListServlet extends HttpServlet {

    private final SpringTemplateEngine templateEngine;
    private final BookService bookService;


    public BookListServlet(SpringTemplateEngine templateEngine, BookService bookService) {
        this.templateEngine = templateEngine;
        this.bookService = bookService;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String title = req.getParameter("title");
        String ratingStr = req.getParameter("averageRating");

        double minRating = 0;
        if (ratingStr != null && !ratingStr.isEmpty()) {
            try {
                minRating = Double.parseDouble(ratingStr);
            } catch (NumberFormatException e) {
                minRating = 0;
            }
        }

        // Filter books
        double finalMinRating = minRating;
        var books = this.bookService.listAll().stream()
                .filter(book -> title == null || title.isEmpty() || book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(book -> book.getAverageRating() >= finalMinRating)
                .toList();

        context.setVariable("ipAddress", req.getRemoteAddr());
        context.setVariable("userAgent", req.getHeader("user-agent"));
        context.setVariable("errorMessage", req.getParameter("errorMessage"));
        context.setVariable("books", books);

        templateEngine.process("listBooks.html", context, resp.getWriter());
    }


}
