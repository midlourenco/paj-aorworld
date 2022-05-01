import React from "react"
import { useState, useEffect } from "react";
//Redirect replace by Naviagate
import {Navigate} from 'react-router-dom'
//https://react-hook-form.com/
import { useForm } from "react-hook-form";
import {FormattedMessage ,useIntl} from "react-intl";
import { Step, Steps, useSteps } from 'chakra-ui-steps';
import {
    Flex,
    Heading,
    Stack,
    Image,
    Box,
    Link,
    Text,

} from "@chakra-ui/react";
import {  AddIcon } from '@chakra-ui/icons'
import RedirectButton from "../components/RedirectButton";
import ContentStep1 from "../components/sections/StepsCreateProj/ContentStep1"

import useFetch from 'use-http';
import { setAppError } from '../redux/actions'
import { connect } from 'react-redux'

  //**********************************************MAIN FUNCTION !!!!!*************************************************************************** */

function NewProject() {
    //https://stackoverflow.com/questions/39630620/react-intl-how-to-use-formattedmessage-in-input-placeholder
  const intl = useIntl();

 //função para fazer o request ao servidor
 const { get, post, response, loading, error } = useFetch();


  //********************************************** UseStates*************************************************************************** */

  const [data, setData]= useState("");
  const [restResponse, setRestResponse]=useState("");
  
  const [input, setInput] = useState([]); //input para as keywords
  const [keywords, setKeywords] = useState([]);// lista com as keywords



  //**********************************************FORMULARIO*************************************************************************** */

  //função de gestão dos dados do formulário
  const {register, handleSubmit, formState: {errors}}= useForm();
  //const onSubmit = values => console.log(values); 
  //const handleSubmit(data){setData(data), console.log(data) )};
  const onSubmit = async(data, e) => {
    
    data.keywords=keywords;
    if(data.visibility=="public")  data.visibility=true;
    if(data.visibility=="private")  data.visibility=false;
    console.log(data, e);

    const createdNews = await post('projects', data)
    if (response.ok) {
        console.log("projecto criada com sucesso ", createdNews);
        setRestResponse("OK");
       
        setAppError("");
     
       
      } else if(response.status==401) {
        console.log("credenciais erradas? " + error)
        setRestResponse("NOK");
       
        setAppError('error_fetch_login_401');
    }else{
        console.log("houve um erro no fetch " + error)
        if(error && error!=""){
            setRestResponse("NOK");
           
            setAppError(  error );
        }else{
            setRestResponse("NOK");
           
            setAppError(  "error_fetch_generic" );
        }
    }

    //response.ok
    //nextStep
    //<Flex p={4}>

  }
  const onError = (errors, e) => console.log(errors, e);


    //**********************************************FUNÇOES KEYWORDS*************************************************************************** */
    
    const saveInputKeyword = e => {
      setInput(e.target.value);
    };

    const handleAddNewKeyword = ()=>{
      if(!keywords.includes(input)){
        setKeywords(prevState=>[...prevState, input]);
        setInput("")
      }
    }

    const handleDeleteTag = (keywordToDelete)=>{
      console.log("carreguei na cruz da keyword... a eliminar "+keywordToDelete )
      // keywords.pop(keywordToDelete);
      setKeywords(keywords.filter((k)=>{
        if(k==keywordToDelete){
          return false;
        }
        return true;
      }));
    }
  //**********************************************USE FETCH*************************************************************************** */

 /**
     * use effect À escuta da variável que obriga ao scroll down
      */
  // useEffect(() => {
  //   window.scrollTo(0,document.body.scrollHeight);
  // },[])


  

  //**********************************************RENDER RETURN FUNÇAO PRINCIPAL*************************************************************************** */
 
  return (
    <Flex
        flexDirection="column"
        width="100wh"
        minHeight="83vh"
        backgroundColor="gray.200"
        justifyContent="center"
        alignItems="center"
        
    >
      <Stack
          flexDir="column"
          mb="2"
          justifyContent="center"
          alignItems="center"
      >

        <Heading mt={20}  color="teal.400"> {intl.formatMessage({id: 'create_project'})}</Heading>
        
        <Box  minW={{ base: "90%", md: "468px" }} width={"xl"}
        maxWidth={"xl"}>
          <Flex flexDir="column" width="100%">
       

          <form onSubmit={ handleSubmit (onSubmit, onError)}>

          {restResponse==""?
          <ContentStep1 
            errors ={errors} 
            register={register} 
            input={input}
            keywords={keywords}
            saveInputKeyword={saveInputKeyword}
            handleAddNewKeyword={handleAddNewKeyword}
            handleDeleteTag={handleDeleteTag}
            
            ></ContentStep1>
            : restResponse=="OK"?
              <Text my={5} color="green">{intl.formatMessage({id: 'sucess_rest_response'})}</Text>
              : restResponse=="NOK"?
                <Text my={5} color="red"> {intl.formatMessage({id: 'error_rest_response'})}: {error} </Text>
                :null
          }   

           
          </form>
          </Flex>
        {/* //   {restResponse=="OK"?
        //   <Text my={5} color="green">{intl.formatMessage({id: 'sucess_rest_response'})} </Text>
        //   : restResponse=="NOK"?
        //   <Text my={5} color="red"> Houve um problema ao guardar a informação </Text>
        //   :null
        // } */}
          <Box>
            <RedirectButton path="/projects" description={intl.formatMessage({id: "_back_to_projects" })} />
          </Box>
        </Box>
      </Stack>
    
    </Flex>
  );
}

export default NewProject;




 //**********************************************GESTAO DOS STEPPERS *************************************************************************** */
 //função para o stepper
//  const { nextStep, prevStep, setStep, reset, activeStep } = useSteps({
//   initialStep: 0,
// });


  // const content1 =(
  //   <ContentStep1 
  //   errors ={errors} 
  //   register={register} 
  //   input={input}
  //   keywords={keywords}
  //   saveInputKeyword={saveInputKeyword}
  //   handleAddNewKeyword={handleAddNewKeyword}
  //   handleDeleteTag={handleDeleteTag}
  //   nextStep={nextStep}
  //   ></ContentStep1>
  // )
  


  // const steps = [
  //   { label: intl.formatMessage({id: 'project_details'}) , content: content1 },

  // ];


{/* <Steps activeStep={activeStep}>
              {steps.map(({ label, content }) => (
                <Step label={label} key={label} justifyContent={"center"} >
                  {content}
                </Step>
              ))}
            </Steps> */}
        
            {/* {activeStep === steps.length ? (
              <Flex p={4}>
                <Button mx="auto" size="sm"  type="submit">
                  Criar projeto
                </Button>
              </Flex>
            ) : (
              <Flex width="100%" justify="flex-end">
                <Button
                  isDisabled={activeStep === 0}
                  mr={4}
                  onClick={prevStep}
                  size="sm"
                  variant="ghost"
                >
                  Prev
                </Button>
                <Button size="sm" onClick={nextStep}>
                  {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
                </Button>
              </Flex>
            )} */}