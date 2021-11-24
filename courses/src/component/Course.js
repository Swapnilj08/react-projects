import axios from 'axios';
import React from 'react';
import { Redirect, Route } from 'react-router';
import { Button, Card, CardBody, CardSubtitle, CardText, CardTitle } from'reactstrap';
import { base_url } from '../api/BootApi';

import {Link} from 'react-router-dom'




const Course=(props)=>{
 

  //function to delete data from server
  const deleteCourse=(id)=>{
    axios.delete(`${base_url}/courses/delete/${id}`,).then(
    (response)=>{

      props.update(id);
    },
    (error)=>{
      console.log(error);
    }

    )
  }
 

    return(
        <div>
            <Card className="container text-center" >
              <CardBody >
                <CardTitle tag="h5">{props.item.title}</CardTitle>
                  <CardSubtitle tag="h6">{props.item.description}</CardSubtitle>
                    <br/>

                  <Button color="danger" onClick={()=>{deleteCourse(props.item.id)}}>Delete</Button>
                  <Link  className="btn btn-warning mr-2 ml-2" to={`/Update/${props.item.id}`}  action>Update</Link>

                </CardBody>

            </Card>
         </div>
    );
}

export default Course;


