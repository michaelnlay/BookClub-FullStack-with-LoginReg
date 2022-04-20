package com.ml.bookclub.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ml.bookclub.models.Book;
import com.ml.bookclub.models.LoginUser;
import com.ml.bookclub.models.User;
import com.ml.bookclub.services.BookService;
import com.ml.bookclub.services.UserService;

@Controller
public class HomeController {

	// Injecting out UserService
		
		private final UserService userServ;
		private final BookService bookServ;
		
		//pass them in as dependency
		public HomeController( UserService userServ, BookService bookServ) {
			this.userServ = userServ;
			this.bookServ = bookServ;
		}
	
	//=======================================================================
	//Render Login / Register Page
	//=======================================================================
	
	@GetMapping("/")
    public String index(Model model) {
    
        // Bind empty User and LoginUser objects to the JSP
        // to capture the form input
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "index.jsp";
    }
	
	//=======================================================================
	//Process Register Route
	//=======================================================================
	
	@PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, 
            BindingResult result, Model model, HttpSession session) {
        
        // TO-DO Later -- call a register method in the service 
        // to do some extra validations and create a new user!
        
	 	userServ.register(newUser, result);
	 	
        if(result.hasErrors()) {
            // Be sure to send in the empty LoginUser before 
            // re-rendering the page.
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }
        
        // No errors! 
        // TO-DO Later: Store their ID from the DB in session, 
        // in other words, log them in.
        session.setAttribute("user_id", newUser.getId());
        return "redirect:/dashboard";
    
    }
	
	//=======================================================================
	//Process Login Route
	//=======================================================================
	@PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
            BindingResult result, Model model, HttpSession session) {
        
        // Add once service is implemented:
        // User user = userServ.login(newLogin, result);
    	User user = userServ.login(newLogin, result);
        if(result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }
    
        // No errors! 
        // TO-DO Later: Store their ID from the DB in session, 
        // in other words, log them in.
        session.setAttribute("user_id", user.getId());
        return "redirect:/dashboard";
    }
    
	
	//=======================================================================
	//Logout Route
	//=======================================================================
		
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); //is clearing your session out
		return "redirect:/";
	}
	
	
	//=======================================================================
	//Render Dashboard Route
	//=======================================================================
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model,
			RedirectAttributes flash) {
		Long userId= (Long) session.getAttribute("user_id");
		if(userId ==null) {
			//below flash, pass in the index.jsp file
			flash.addFlashAttribute("login","Please login or register before entering the site!");
			return "redirect:/";}
		
		//go back to user Services and look for getUserInfo method, pull info out and pass to front end.
		
		User user = userServ.getUserInfo(userId); //#1 now Controller has thing to call on, which is geteUserInfo()
		
		//and go to user Service. Once it come back here from the Resp ->Service -> Controller...the user in User is  fully formed instance from my DB. 
		//getUserInfo() was created in User Services under find One user by ID.
		
		model.addAttribute("loggedUser",user);
		//loggedUser to show on HTML, and user is what we just queried for.
		//can also be done..model.addAttribute("loggedUser",userServ.getUserInfo(userId)
		
		//show all Book to JSP
		List<Book> books = bookServ.getAllBooks();
		model.addAttribute("books", books);
		
		return "dashboard.jsp";
	}
	
	//=======================================================================
	//Create Routes
	//=======================================================================
   @GetMapping("/newBook")
   public String newBook(HttpSession session, RedirectAttributes flash, Model model ) {
	   Long userId= (Long) session.getAttribute("user_id");
	   
	   //below prevent stranger to get into the Add new Book page before log in or register
		if(userId ==null) {
			//below flash, pass in the newBook.jsp file
			flash.addFlashAttribute("login","Please login or register before entering the site!");
			return "redirect:/";
   }
		
		model.addAttribute("userId",userId); //userId that we pulled out from session
		model.addAttribute("book", new Book()); //new Book() is a new instance of a book
		
		return "newBook.jsp";
}

   @PostMapping("/createBook")
   public String createBook(@Valid @ModelAttribute("book") Book book, 
           BindingResult result, HttpSession session, Model model) {
	   if(result.hasErrors()) {
		   Long userId= (Long) session.getAttribute("user_id");
		   model.addAttribute("userId", userId);
		   
		   return "newBook.jsp";
	   }else {
		   bookServ.createBook(book);
		   return "redirect:/dashboard";
	   }
	
   }
   
 //=======================================================================
//Show One Book - Route
//=======================================================================
   
   @GetMapping("/oneBook/{id}")
   public String showOne(@PathVariable("id")Long bookId, 
		   HttpSession session,
		   Model model,
		   RedirectAttributes flash) {
	   
	   Long userId= (Long) session.getAttribute("user_id");
		if(userId ==null) {
			
			flash.addFlashAttribute("login","Please login or register before entering the site!");
			return "redirect:/";} 
	   
	   Book book = bookServ.findOneBook(bookId);
	   model.addAttribute("book", book);
	   
	   
	   return "oneBook.jsp";
   }
 //=======================================================================
 //Edit Routes
 //=======================================================================
	@GetMapping("/editBook/{id}")
 	public String editBook(@PathVariable("id")Long bookId, 
		   HttpSession session,
		   Model model,
		   RedirectAttributes flash) {
	   
	   Long userId= (Long) session.getAttribute("user_id");
		if(userId ==null) {
			
			flash.addFlashAttribute("login","Please login or register before entering the site!");
			return "redirect:/";} 
	   
	   Book book = bookServ.findOneBook(bookId);
	   model.addAttribute("book", book);
	   
	   return "editBook.jsp";
 }
	@PutMapping("updateBook/{id}")
	public String updateBook(@Valid @ModelAttribute("book") Book book, BindingResult result) {
		   if(result.hasErrors()) {
			 
			   
			   return "editBook.jsp";
		   }else {
			   bookServ.updateBook(book);
			   return "redirect:/dashboard";
		   }

	}
	
 //=======================================================================
 //Delete Route
 //=======================================================================
//	@DeleteMapping won't work without the form:form
	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable("id") Long bookId) {
		bookServ.deleteBook(bookId);
		
		return "redirect:/dashboard";
}
	
	
}
