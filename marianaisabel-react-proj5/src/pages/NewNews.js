import React from "react"
import { useState, useEffect } from "react";
//Redirect replace by Naviagate
import {Navigate} from 'react-router-dom'
//https://react-hook-form.com/
import { useForm } from "react-hook-form";
import useFetch from 'use-http';
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
import ContentStep1 from "../components/sections/StepsCreateNews/ContentStep1"
import ContentStep2 from "../components/sections/StepsCreateNews/ContentStep2"
import ContentStep3 from "../components/sections/StepsCreateNews/ContentStep3"

import RedirectButton from "../components/RedirectButton";


  //**********************************************MAIN FUNCTION !!!!!*************************************************************************** */
  //TODO: 
  function setAppError(error){
    console.log(error)
}

function NewNews() {
    //https://stackoverflow.com/questions/39630620/react-intl-how-to-use-formattedmessage-in-input-placeholder
    const intl = useIntl();


  const { post, del, response, loading, error } = useFetch();
  const {register, handleSubmit, trigger, watch, getValues,formState: {errors}}= useForm(
    {defaultValues: {
      users: [],
      projects: [],
    }});

    
  const { nextStep, prevStep, setStep, reset, activeStep } = useSteps({
    initialStep: 0,
  });
  //   const { fields, append, prepend, remove, swap, move, insert } = useFieldArray<
  //   newKeywords,
  //   'keywords' 
  // >({
  //   control,
  //   name: 'keywords',
  // });

  //const onSubmit = values => console.log(values); 
  //const handleSubmit(data){setData(data), console.log(data) )};




  const [data, setData]= useState("");
  const [restResponse, setRestResponse]=useState("");
  
  const [showStepperButtons, setShowStepperButtons]=useState(false);




  //**********************************************PESQUISA KEYWORDS*************************************************************************** */
 
  const [input, setInput] = useState([]);
  const saveInputKeyword = e => {
    setInput(e.target.value);
  };
  const [keywords, setKeywords] = useState([]);
  const handleAddNewKeyword = ()=>{
    if(!keywords.includes(input)&&input.trim()!=""){
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
   //**********************************************FORMULARIO SUBMIT...*************************************************************************** */
 
    
      const onSubmit = async(data, e) => {
      
        data.keywords=keywords;
        if(data.projects==0)  data.projects=[];
        if(data.users==0)  data.users=[];
        if(data.visibility=="public")  data.visibility=true;
        if(data.visibility=="private")  data.visibility=false;
      
        console.log(data, e,data.visibility);
        const createdNews = await post('news', data)
        if (response.ok) {
            console.log("noticia criada com sucesso ", createdNews);
            setRestResponse("OK");
           
            setAppError("");
            nextStep();
           
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
    
      
  //**********************************************USE EFFECT*************************************************************************** */
 
  /**
     * use effect À escuta da variável keyword vai permitir que a lista seja actualizada visualmente dp de apagar
     */
     useEffect(() => {
    window.scrollTo(0,document.body.scrollHeight);
  },[keywords])
   
  /**
     * use effect À escuta da variável que obriga ao scroll down
     */
  //  useEffect(() => {
  //   window.scrollTo(0,document.body.scrollHeight);
  // },[])
   
  //*********************************************STEPPER *************************************************************************** */
 
//https://jeanverster.github.io/chakra-ui-steps-site/

  const content1 =(
    <ContentStep1 
    errors ={errors} 
    register={register} 
    input={input}
    keywords={keywords}
    saveInputKeyword={saveInputKeyword}
    handleAddNewKeyword={handleAddNewKeyword}
    handleDeleteTag={handleDeleteTag}
    setShowStepperButtons={setShowStepperButtons}
    showStepperButtons={showStepperButtons}
    trigger={trigger}
    nextStep={nextStep}
    ></ContentStep1>
  )
  
  const content2 = (
    <ContentStep2    
    register={register}   
    nextStep={nextStep}
    prevStep={prevStep}
    trigger={trigger}
    errors ={errors} 



    />
  );

  const content3 = (
    <ContentStep3  
    register={register}   
    nextStep={nextStep}
    prevStep={prevStep}
    trigger={trigger}
    errors ={errors} 
    />
  );

  const steps = [
    { label: intl.formatMessage({id: 'news_details'}) , content: content1 },
    { label: intl.formatMessage({id: 'associated_users'}) , content: content2 },
    { label: intl.formatMessage({id: 'associated_projects'}) , content: content3 }
  ]
  //**********************************************RENDER RETURN FUNCÇAO PRINCIPAL*************************************************************************** */
 
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

        <Heading mt={20}  color="teal.400"> {intl.formatMessage({id: 'create_article'})}</Heading>
        <Box  minW={{ base: "90%", md: "468px" }} width={"xl"}
        maxWidth={"xl"}>
          <Flex flexDir="column" width="100%">
          
          <form onSubmit={ handleSubmit (onSubmit, onError)}>
            <Steps activeStep={activeStep}>
              {steps.map(({ label, content }) => (
                <Step label={label} key={label} >
                  {content}
                </Step>
              ))}
            </Steps>
          </form>
          </Flex>
        {restResponse=="OK"?
        <Text my={5} color="green">{intl.formatMessage({id: 'sucess_rest_response'})} </Text>
        : restResponse=="NOK"?
        <Text my={5} color="red"> {intl.formatMessage({id: 'error_rest_response'})}: {error} </Text>
        :null
        }
          <Box>
              <RedirectButton path="/news" description={intl.formatMessage({id: "_back_to_news" })} />
          </Box>
        </Box>
      </Stack>
    
    </Flex>
  );
}

export default NewNews;


// ideia para keywords existentes Multi select combobox
// <FormControl p={4}>
// <FormLabel>
//   Select Colors and Flavours <Code>size="md" (default)</Code>
// </FormLabel>
// <Select
//   isMulti
//   name="colors"
//   options={groupedOptions}
//   placeholder="Select some colors..."
//   closeMenuOnSelect={false}
// />
// </FormControl>


            {/* { activeStep === steps.length ? (
                <Flex p={4}>
                  <Button mx="auto" size="sm"  type="submit">
                    Criar Notícia
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
                  <Button size="sm" onClick={handleNextStep}>
                    {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
                  </Button>
                </Flex>
              )
            } */}



// const users = [
//   {id:"11111", firstName: 'Mariana', image: "https://bit.ly/dan-abramov" },
//   {id:"222222",firstName: 'Isabel', image: "https://bit.ly/sage-adebayo"  },
//   {id:"33333",firstName: 'José', image: "https://bit.ly/sage-adebayo"  },
//   {id:"44444",firstName: 'Ricardo', image: "https://bit.ly/dan-abramov"  },
//   {id:"55555", firstName: 'Mariana', image: "https://bit.ly/dan-abramov" },
//   {id:"6666",firstName: 'Isabel', image: "https://bit.ly/sage-adebayo"  },
//   {id:"77777",firstName: 'José', image: "https://bit.ly/sage-adebayo"  },
//   {id:"555558",firstName: 'Ricardo', image: "https://bit.ly/dan-abramov"  },
//   {id:"96666", firstName: 'Mariana', image: "https://bit.ly/dan-abramov" },
//   {id:"1340",firstName: 'Isabel', image: "https://bit.ly/sage-adebayo"  },
//   {id:"15551",firstName: 'José', image: "https://bit.ly/sage-adebayo"  },
//   {id:"124444",firstName: 'Ricardo', image: "https://bit.ly/dan-abramov"  },

// ];

// const projectslist=[
//   {id:"11111", title: 'hgjk', image: "https://bit.ly/dan-abramov" },
//   {id:"22222",title: 'jk', image: "https://bit.ly/sage-adebayo"  },
//   {id:"33333",title: 'jk', image: "https://bit.ly/sage-adebayo"  },
//   {id:"44444",title: '5r6tu', image: "https://bit.ly/dan-abramov"  },
//   {id:"555555", title: '6tuhkl', image: "https://bit.ly/dan-abramov" },
//   {id:"66666",title: 'hfgvb ', image: "https://bit.ly/sage-adebayo"  },
//   {id:"7777",title: ' nbm', image: "https://bit.ly/sage-adebayo"  },
//   {id:"8888",title: 'cnmjh', image: "https://bit.ly/dan-abramov"  },
//   {id:"99999", title: 'htfjgkhl', image: "https://bit.ly/dan-abramov" },
//   {id:"11222",title: 'chjgh', image: "https://bit.ly/sage-adebayo"  },
//   {id:"133331",title: 'chjk', image: "https://bit.ly/sage-adebayo"  },
//   {id:"12444",title: 'fgjhk', image: "https://bit.ly/dan-abramov"  },

// ];
