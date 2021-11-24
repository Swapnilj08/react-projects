import { CardBody,Card, Container } from "reactstrap"

const Header=()=>{

    return(
        
    <div>
        <Card className="my-2 bg-warning">
            <CardBody>
                <h1 className="text-center my-2">Welcome to Course Application</h1>
            </CardBody>
        </Card>      
    </div>
    );
}

export default Header;