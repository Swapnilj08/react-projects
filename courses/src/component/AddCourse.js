import { Button, Container, Input } from 'reactstrap';
import React, { Fragment, useEffect, useState } from 'react';
import { Form, FormGroup,Label } from 'reactstrap';
import axios from 'axios';
import { base_url } from '../api/BootApi';
import { toast } from 'react-toastify';
//import { browserHistory } from 'react-router'

const AddCourse=()=>{
    useEffect(
        ()=>{
            document.title="Add course";
        },[]
    )

const [course,setCourse]=useState({});

const handleForm=(e)=>{
    console.log(course);
    postDataToServer(course);
    e.preventrDefault();
    //browserHistory.push("/viewcourse");
};

//function to send data to Server
const postDataToServer=(data)=>{
    axios.post(`${base_url}/courses`,data).then(
        (response)=>{
            console.log(response);
            toast.success("course Added");
        },(error)=>{
console.log(error);
        }
    )
}

    return(
<Fragment>
    <h1 className="text-center my-3">Fill Application</h1>
    <Form onSubmit={handleForm}>
        <FormGroup>
            <Label for="courseid">Course ID</Label>
            <Input type="text" name="courseid" id="courseid" placeholder="Enter Here"
            onChange={(e)=>{setCourse({...course,id:e.target.value});
            }}
        />
        </FormGroup>

        <FormGroup>
            <Label>Course Title</Label>
            <Input type="text" id="coursetitle" placeholder="Enter Here"
            onChange={(e)=>{setCourse({...course,title:e.target.value});
            }}
            />
        </FormGroup>

        <FormGroup>
            <Label>Course Description</Label>
            <Input type="textarea" id="courseDescription" placeholder="Enter Description Here" 
            onChange={(e)=>{setCourse({...course,description:e.target.value});
            }}
            />
        </FormGroup>
        <Container className="text-center">
            <Button type="submit" color="success">Add Course</Button>
            <Button color="warning ml-2">Clear </Button>
        </Container>
    </Form>

</Fragment>

    );
};

export default AddCourse;