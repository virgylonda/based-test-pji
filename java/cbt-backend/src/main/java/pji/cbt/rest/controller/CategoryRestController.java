package pji.cbt.rest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.jsonwebtoken.Claims;
import pji.cbt.entities.Category;
import pji.cbt.entities.Organization;
import pji.cbt.services.CategoryService;


@RestController
@RequestMapping("/secure")
public class CategoryRestController {
	
	@Autowired
	HttpSession session; 
	
	@Autowired
	private CategoryService ctgSvc;
	
	
	public CategoryRestController(){
	}
	private static Logger logger = Logger.getLogger(CategoryRestController.class);
	
	
	   @ModelAttribute("claims")
	    public Claims getClaims(HttpServletRequest request) 
	    {
	        return (Claims) request.getAttribute("claims");
	    }
	
	 /**
     * Create 
     * @param 	category
     * @param 	ucBuilder
     * @method	POST
     * @return 	New Category Question HttpStatus.CREATED
     */
    @RequestMapping(value = "/createcategory", method = RequestMethod.POST)
    public ResponseEntity<Void> createCategory(@ModelAttribute("claims") Claims claims, @RequestBody Category category, UriComponentsBuilder ucBuilder) {
    	logger.info("Creating Category " + category.getDescription());
    	
        Integer strOrgId=(Integer) claims.get("orgId");
		logger.debug("parsed orgId: "+strOrgId);	
		Organization o = new Organization();
		o.setId(strOrgId.longValue());
		category.setOrganization(o);

    	if(ctgSvc.exists(category)){
    		logger.info("a category with id "+category.getIdCategory()+" already exists");
    		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    	}
    	
    	ctgSvc.createCategory(category);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/category/getCategoryById/{id}").buildAndExpand(category.getIdCategory()).toUri());
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
    	logger.debug("Updating Category " + id);
        
        Category currentCategory = ctgSvc.findOneCategory(id);
         
        if (currentCategory==null) {
            logger.warn("Category with id " + id + " not found");
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
    @RequestMapping(value = "/deletecategory/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") long id) {
    	logger.info("Fetching & Deleting Category with id " + id);
   	 
        Category category = ctgSvc.findOneCategory(id);
        
        if (category == null) {
           logger.warn("Unable to delete. Category with id " + id + " not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
 
        ctgSvc.deleteOne(id);
        return new ResponseEntity<Category>(HttpStatus.OK);
    }
	
    /**
	 * Get All Category Question
	 * @method	GET
	 * @return	GetAllCategory
	 */
	@RequestMapping(path="/getallcategory",method=RequestMethod.GET)
	public List<Category> getAllCategory(@ModelAttribute("claims") Claims claims){
        Integer strOrgId=(Integer) claims.get("orgId");
		logger.debug("parsed orgId: "+strOrgId);	
		return ctgSvc.findAllCategory(strOrgId.longValue());
	}
	
	 /**
		 * @param  		id
		 * @method		GET
		 * @return      get a category by id
		 */
	 @RequestMapping(value = "/getCategoryById/{id}", method = RequestMethod.GET)
	 public ResponseEntity<Category> getCategoryById(@PathVariable("id") int id) {
		 logger.info("Fetching category with id : "+id);
		 
		 Category category = ctgSvc.findOneCategory(id);
		 
		 if(category == null){
			 logger.warn("category with id "+id+ " not found");
			 return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
		 }
		 
		 return new ResponseEntity<Category>(category, HttpStatus.OK);
	    }
	
}
