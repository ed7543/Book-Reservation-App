package mk.ukim.finki.wp.lab.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.repository.BookReservationRepository;
import mk.ukim.finki.wp.lab.service.BookReservationService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet(name = "BookReservationServlet", urlPatterns = "/servlet/bookReservation")
public class BookReservationServlet extends HttpServlet {
    private final SpringTemplateEngine templateEngine;
    private final BookReservationService bookReservationService;

    public BookReservationServlet(SpringTemplateEngine templateEngine, BookReservationService bookReservationService) {
        this.templateEngine = templateEngine;
        this.bookReservationService = bookReservationService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/servlet/books");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookTitle = req.getParameter("bookTitle");
        String readerName = req.getParameter("readerName");
        String readerAddress = req.getParameter("readerAddress");
        req.getParameter("numberOfCopies");
        int numberOfCopies;

        try {
            numberOfCopies = Integer.parseInt(req.getParameter("numberOfCopies"));
        } catch (NumberFormatException e) {
            resp.sendRedirect("/?errorMessage=Number of copies must be a valid number");
            return;
        }

        BookReservation reservation;
        try {
            reservation = bookReservationService.placeReservation(bookTitle, readerName, readerAddress, numberOfCopies);
        } catch (IllegalArgumentException e) {
            resp.sendRedirect("/?errorMessage=" + e.getMessage());
            return;
        }

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);
        context.setVariable("reservation", reservation);
        context.setVariable("ipAddress", req.getRemoteAddr());

        templateEngine.process("reservationConfirmation.html", context, resp.getWriter());

    }


}
