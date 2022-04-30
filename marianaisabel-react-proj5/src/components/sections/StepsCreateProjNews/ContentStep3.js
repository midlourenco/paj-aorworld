import React, {useState, useEffect } from 'react'
import {FormattedMessage ,useIntl} from "react-intl";
import {
    Flex,
    Stack,
    FormControl,
    Checkbox,
    Button,
    Grid,
} from "@chakra-ui/react";
import {  AddIcon } from '@chakra-ui/icons'
import useFetch from 'use-http';
// import { setAppError } from '../redux/actions'
import { connect } from 'react-redux'



function ContentStep3({register,nextStep,prevStep,trigger,...props}) {
    //função para fazer o request ao servidor
    const { get, post, response, loading, error } = useFetch();
    const intl = useIntl();
    //TODO: 
    function setAppError(error){
    console.log(error)
    }
    const [news, setNews]=useState([]);
    useEffect(async()=>{
        const getNews = await get('/news')
        if (response.ok) {
            console.log("dentro do ok do get news vindos do rest - setp3")
            setNews(getNews);
            setAppError("");
            //setLogin(true);
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_generic');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
                setAppError(  error);
            }else{
                setAppError(  "error_fetch_generic" );
            }
        }
    
    },[])

    return (<Flex justifyContent={"center"} py={4}>
        {error && 'Error!'}
        {loading && 'Loading...'}
        <Stack
        spacing={4}
        p="1rem"
        backgroundColor="whiteAlpha.900"
        boxShadow="md"
        minH={"300px"}
        width={"100%"}
        >
            <FormControl>
                <Grid  templateColumns='repeat(2, 1fr)' >
                    {news.map(n => (
                    <Checkbox  
                    {...register("projects", {
                        valueAsNumber: true
                    }) }  
                    value={n.id}  
                    colorScheme='teal' 
                    m={3} 
                    key={n.id} 
                    >
                        {n.title}
                    </Checkbox>        
                    ))}          
                </Grid>
            </FormControl>

            <Flex width="100%" justify="flex-end">
            <Button
                mr={4}
                onClick={prevStep}
                size="sm"
                variant="ghost"
            >
                 {intl.formatMessage({id: 'prev'})} 
            </Button>
            <Button size="sm" type="submit" > 
                {intl.formatMessage({id: 'create_news'})} 
            </Button>
         </Flex>
        </Stack>
    </Flex>
    )
}

export default ContentStep3