import React from 'react';
import { Row } from "reactstrap";
import { ToastContainer } from 'react-toastify';
import { Col } from 'reactstrap';
import Header from './Header';
import Menu from './Menu';
import { BrowserRouter as Router,Route } from "react-router-dom";
import Home from './Home';
import AddCourse from './AddCourse'
import Course from './Course'
import AllCourses from './AllCourses';
import UpdateCourse from '../component/UpdateCourse';


const App=()=>{
    
    return(
        
<div className="container">
    <Router>
        <ToastContainer position="bottom-center"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover/>
        <Header/>
        <Row>
        <Col md={3}>
            <Menu/>
        </Col>
        <Col md={9}>
            <Route path="/" component={Home} exact/>
            <Route path="/addcourse" component={AddCourse} exact/>
            <Route path="/viewcourse" component={AllCourses} exact/>
            <Route path="/Update/:id" component={UpdateCourse} exact/>
        </Col>
        </Row>
    </Router>
</div>
    );
}
export default App;