package ch.gibm.bean;

import ch.gibm.entity.User;
import ch.gibm.facade.UserFacade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@ManagedBean
public class LoginBean extends AbstractBean {

    @ManagedProperty(value = UserBean.DI_NAME)
    private UserBean userBean; //1
    private String userName; //2
    private String password;

    public String login() { //3
        UserFacade userFacade = new UserFacade(); //4
        User user = userFacade.isValidLogin(userName, password); //5
        if (user != null) {
            userBean.setUser(user); //6
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest)

                    context.getExternalContext().getRequest();

            request.getSession().setAttribute("user", user); //7
            return "/pages/protected/index.xhtml"; //8
        }
        displayErrorMessageToUser("Check username and/or password");
        return null;
    }

    public void setUserBean(UserBean userBean) { //9
        this.userBean = userBean;
    }

}
