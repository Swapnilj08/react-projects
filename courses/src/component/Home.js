import React from 'react';
import { Button } from 'reactstrap';
import { Jumbotron } from "reactstrap";
import { toast,ToastContainer } from "react-toastify";
import Course from './Course';


const start=()=>toast("Your Journey is started");
const Home = ()=>{
return(
    
    <div className="text-center">
    
    <Jumbotron> 
<h1>Welcome To The Course World </h1>
<p>Select Course You Are Interested In</p>
<Button color="primary" onClick={start} >Get Started</Button>
</Jumbotron>

</div>
);

}

export default Home;