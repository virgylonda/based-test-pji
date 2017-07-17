package pji.cbt.rest.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import pji.cbt.entities.Category;
import pji.cbt.services.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryRestController {
	
	@Autowired
	HttpSession session; 
	
	@Autowired
	private CategoryService ctgSvc;
	
	
	public CategoryRestController(){
	}
	private static Logger logger = Logger.getLogger(CategoryRestController.class);
	
	
	
	 /**
     * Create 
     * @param 	category
     * @param 	ucBuilder
     * @method	POST
     * @return 	New Category Question HttpStatus.CREATED
     */
    @RequestMapping(value = "/createcategory", method = RequestMethod.POST)
    public ResponseEntity<Void> createCategory(@RequestBody Category category, UriComponentsBuilder ucBuilder) {
    	logger.info("Creating Category " + category.getDescription());

    	try {
    		ctgSvc.createCategory(category);
    	}catch (Exception e) {
    		System.out.println(e);
		}
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/testers/category/{id}").buildAndExpand(category.getIdCategory()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
	
    /**
     * Update
     * @param   id
     * @param   category
     * @method	PUT
     * @return  Category Question  HttpStatus.OK
     */
    @RequestMapping(value = "/updatecategory/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Category> updateCategory(@PathVariable("id") long id, @RequestBody Category category) {
    	logger.info("Updating Category " + id);
        
        Category currentCategory = ctgSvc.findOneCategory(id);
         
        if (currentCategory==null) {
            System.out.println("Category with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        
        currentCategory.setQuestionCategory(category.getQuestionCategory());
        currentCategory.setDescription(category.getDescription());
        currentCategory.setQuestionType(category.getQuestionType());
        currentCategory.setTimeLimit(category.getTimeLimit());
                    
        ctgSvc.updateCategory(currentCategory);
        return new ResponseEntity<Category>(currentCategory, HttpStatus.OK);
    }
	
    /**
     * Delete by Category Question
     * @param	id
     * @method	DELETE
     * @return 	HttpStatus.NO_CONTENT
     */
    @RequestMapping(value = "/deletecategory{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") long id) {
    	logger.info("Fetching & Deleting Category with id " + id);
   	 
        Category category = ctgSvc.findOneCategory(id);
        if (category == null) {
            System.out.println("Unable to delete. Category with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
 
        ctgSvc.deleteOne(id);
        return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
    }
	
    /**
	 * Get All Category Question
	 * @method	GET
	 * @return	GetAllCategory
	 */
	@RequestMapping(path="/getallcategory",method=RequestMethod.GET)
	public List<Category> getAllCategory(){
		return ctgSvc.findAllCategory();
	}
	
	 /**
		 * @param  		id
		 * @method		GET
		 * @return      get a category by id
		 */
	 @RequestMapping(value = "/getCategoryById/{id}", method = RequestMethod.GET)
	 public ResponseEntity<Category> getCategoryById(@PathVariable("id") int id) {
		 Category category = ctgSvc.findOneCategory(id);
		 return new ResponseEntity<Category>(category, HttpStatus.OK);
	    }
	    
	 /**
		 * List Category Detail by id
		 * @param 	id
		 * @param	redirectAttributes
		 * @param	model
		 * @method	GET
		 * @return	findOneCategory
		 */
		@RequestMapping(path = "/getCategoryDetail/{id}", method=RequestMethod.GET)
		public Category getOneCategory(@PathVariable long id, RedirectAttributes redirectAttributes, Model model) {
			Category category = this.ctgSvc.findOneCategory(id);
			model.addAttribute("data", category);
			return category;
		}
	
}