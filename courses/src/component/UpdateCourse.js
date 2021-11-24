import { Button, Container, Input } from 'reactstrap';
import React, { Fragment, useEffect, useState } from 'react';
import { Form, FormGroup,Label } from 'reactstrap';
import axios from 'axios';
import { base_url } from '../api/BootApi';
import { toast } from 'react-toastify';
import {Redirect, useParams} from "react-router-dom";  
import {Link} from 'react-router-dom';

const UpdateCourse=(props)=>{
    const {id}=useParams();
    console.log("update courese >>"+id);
    

const [course,setCourse]=useState({});

const handleForm=(e)=>{
    console.log(course);
    postDataToServer(course);
    //avoid page refresh
    e.preventrDefault();
    
   
};
//function to send data to Server
const postDataToServer=(data)=>{
    axios.put(`${base_url}/courses/update/${id}`,data).then(
        (response)=>{
            console.log(response);
            toast.success("course Updated");
            
        },(error)=>{
console.log(error);
        }
    )
}
//load data from DB for selected user
const loadUser=async()=>{
    console.log("loadUser from update");
    const userdata=await axios.get(`${base_url}/courses/${id}`)
    setCourse(userdata.data);
    console.log(course.id);
};
useEffect(
    ()=>{
        console.log("Use effect from update");
        loadUser();
        document.title="Add course";
    },[]
)

const handleRedirect=()=>{
    console.log("Handel Redirect called");
    <Redirect to="/viewcourse"/>    
}
    return(
<Fragment>
    <h1 className="text-center my-3">Update Application</h1>
    <Form onSubmit={handleForm }>
        <FormGroup>
            <Label for="courseid">Course ID</Label>
            <Input type="text" name="courseid" id="courseid" placeholder="Enter Here"
            onChange={(e)=>{setCourse({...course,id:e.target.value});
            }}
            value={course.id} disabled
            />
        </FormGroup>

        <FormGroup>
            <Label>Course Title</Label>
            <Input type="text" id="coursetitle" placeholder="Enter Here"
            onChange={(e)=>{setCourse({...course,title:e.target.value});
            }}
            value={course.title}
            />
        </FormGroup>

        <FormGroup>
            <Label>Course Description</Label>
            <Input type="textarea" id="courseDescription" placeholder="Enter Description Here" 
            onChange={(e)=>{setCourse({...course,description:e.target.value});
            }}
            value={course.description}

            />
        </FormGroup>
        <Container className="text-center">
            <Button type="submit" color="success">Update Course</Button>
            <Link  className="btn btn-primary mr-2 ml-2" to="/viewcourse" action>Cancle</Link>
        </Container>
    </Form>

</Fragment>

    );
};

export default UpdateCourse;