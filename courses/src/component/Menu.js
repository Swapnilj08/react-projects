import { ListGroup } from "reactstrap"
import {Link} from 'react-router-dom'

const Menu=()=>{
return(
    <ListGroup>
    <Link className="list-group-item list-group-item-action" to="/" action>
    Home
    </Link>
    <Link className="list-group-item list-group-item-action" to="/addcourse" action>
    Add Courese
    </Link>
    <Link className="list-group-item list-group-item-action" to="/viewcourse" action>
    View Courses
    </Link>
    <Link className="list-group-item list-group-item-action" to="/Update" action>
    About
    </Link>
    <Link className="list-group-item list-group-item-action" to="#" action>
    Contact
    </Link>
    
    </ListGroup>
    );
}

export default Menu;