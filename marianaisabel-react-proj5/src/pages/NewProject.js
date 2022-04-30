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

 //função para o stepper
 const { nextStep, prevStep, setStep, reset, activeStep } = useSteps({
  initialStep: 0,
});
  //**********************************************FORMULARIO*************************************************************************** */

  //função de gestão dos dados do formulário
  const {register, handleSubmit, formState: {errors}}= useForm();
  //const onSubmit = values => console.log(values); 
  //const handleSubmit(data){setData(data), console.log(data) )};
  const onSubmit = async(data, e) => {
    console.log(data, e);
    data.keywords=keywords;

    const createdNews = await post('news', data)
    if (response.ok) {
        console.log("noticia criada com sucesso ", createdNews);
        setRestResponse("OK");
        setScrollDown(true);
        setAppError("");
        nextStep();
       
      } else if(response.status==401) {
        console.log("credenciais erradas? " + error)
        setRestResponse("NOK");
        setScrollDown(true);
        setAppError('error_fetch_login_401');
    }else{
        console.log("houve um erro no fetch " + error)
        if(error && error!=""){
            setRestResponse("NOK");
            setScrollDown(true);
            setAppError(  error );
        }else{
            setRestResponse("NOK");
            setScrollDown(true);
            setAppError(  "error_fetch_generic" );
        }
    }

    //response.ok
    //nextStep
    //<Flex p={4}>

  }
  const onError = (errors, e) => console.log(errors, e);


  //**********************************************others UseStates*************************************************************************** */

  const [data, setData]= useState("");
  const [restResponse, setRestResponse]=useState("");
  const [scrollDown, setScrollDown]=useState(false)


  //**********************************************PESQUISA KEYWORDS*************************************************************************** */
  const [input, setInput] = useState([]);
  const saveInputKeyword = e => {
    setInput(e.target.value);
  };
  const [keywords, setKeywords] = useState([]);
  const handleAddNewKeyword = ()=>{
    if(!keywords.includes(input)){
      setKeywords(prevState=>[...prevState, input]);
      setInput("")
    }
  }

    //**********************************************FUNÇOES AUXIliares*************************************************************************** */


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
  useEffect(() => {
    window.scrollTo(0,document.body.scrollHeight);
  },[scrollDown])


   //**********************************************GESTAO DOS STEPPERS *************************************************************************** */

  const content1 =(
    <ContentStep1 
    errors ={errors} 
    register={register} 
    input={input}
    keywords={keywords}
    saveInputKeyword={saveInputKeyword}
    handleAddNewKeyword={handleAddNewKeyword}
    handleDeleteTag={handleDeleteTag}
    nextStep={nextStep}
    ></ContentStep1>
  )
  
  // const content2 = (
  //   <ContentStep2  
  //   register={register}   
  //   />
  // );

  // const content3 = (
  //   <ContentStep3  
  //   register={register}   
  //   />
  // );

  const steps = [
    { label: intl.formatMessage({id: 'project_details'}) , content: content1 },

  ];


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
          {errors && 'Error!'}
            {loading && 'Loading...'}

          <form onSubmit={ handleSubmit (onSubmit, onError)}>
            <Steps activeStep={activeStep}>
              {steps.map(({ label, content }) => (
                <Step label={label} key={label} >
                  {content}
                </Step>
              ))}
            </Steps>
        
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
          </form>
          </Flex>
          {restResponse=="OK"?
        <Text my={5} color="green"> Informação guardada com sucesso </Text>
        : restResponse=="NOK"?
        <Text my={5} color="red"> Houve um problema ao guardar a informação </Text>
        :null
        }
          <Box>
            <RedirectButton path="/projects" description={intl.formatMessage({id: "_back_to_projects" })} />
          </Box>
        </Box>
      </Stack>
    
    </Flex>
  );
}

export default NewProject;