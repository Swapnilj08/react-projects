import axios from "axios";
import { useState,useEffect } from "react"
import { toast } from "react-toastify";
import { base_url } from "../api/BootApi";
import Course from'./Course';

const AllCourses=()=>{
useEffect(()=>{
document.title="All Courses";
},[]);


//function to get all date from server 
const getAllCoursesFromServer=()=>{

axios.get(`${base_url}/courses`).then((response)=>{
    console.log(response.data);
    toast.success("Data Load succesfull");
    setCourses(response.data);
},
(error)=>{
    //for error
    console.log(error);
    toast.error("Something went wrong");
}
);}

//calling loading course function
useEffect(()=>
{
    getAllCoursesFromServer();

},[]);

    const[courses,setCourses]=useState([]);


   const  updateCourse=(id)=>{
    setCourses(courses.filter((c)=>c.id!=id))
    };
    return(
        <div>
            <div className="text-center">       
                <h1>AllCoureses</h1>
                <p>List of courses</p>
             </div>

            {
                    courses.length>0 ? courses.map((course)=>
                    <Course key={course.id} item={course} update={updateCourse}/>
                    ):"No course available"
                }
   
        </div>
    );



};

export default AllCourses;

 