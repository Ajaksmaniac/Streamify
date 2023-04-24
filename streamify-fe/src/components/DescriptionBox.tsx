import React from 'react';
import { Container } from 'react-bootstrap';

const DescriptionBox = (props:{description:string}) => {
    return (
        <Container className='h-35 bg-light'style={{height:"100px"}}>
            <h4>Description</h4>
            {props.description}
        </Container>
    );
};

export default DescriptionBox;