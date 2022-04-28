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
    Input,
    Button,
    InputGroup,
    Stack,
    HStack,
    InputLeftElement,
    chakra,
    Image,
    Box,
    Link,
    Text,
    Avatar,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    Checkbox,
    Textarea,
    Badge,
    Tag,
    TagLabel,
    Grid,
    UnorderedList,
    ListItem,
    InputRightElement,
    TagCloseButton
} from "@chakra-ui/react";
import {  AddIcon } from '@chakra-ui/icons'
import RedirectButton from "../components/RedirectButton";
import ContentStep1 from "../components/sections/StepsCreateProjNews/ContentStep1"
import ContentStep2 from "../components/sections/StepsCreateProjNews/ContentStep2"
import ContentStep3 from "../components/sections/StepsCreateProjNews/ContentStep3"
import useFetch from 'use-http';
import { setAppError } from '../redux/actions'
import { connect } from 'react-redux'

const users = [
  {id:"11111", firstName: 'Mariana', image: "https://bit.ly/dan-abramov" },
  {id:"222222",firstName: 'Isabel', image: "https://bit.ly/sage-adebayo"  },
  {id:"33333",firstName: 'José', image: "https://bit.ly/sage-adebayo"  },
  {id:"44444",firstName: 'Ricardo', image: "https://bit.ly/dan-abramov"  },
  {id:"55555", firstName: 'Mariana', image: "https://bit.ly/dan-abramov" },
  {id:"6666",firstName: 'Isabel', image: "https://bit.ly/sage-adebayo"  },
  {id:"77777",firstName: 'José', image: "https://bit.ly/sage-adebayo"  },
  {id:"555558",firstName: 'Ricardo', image: "https://bit.ly/dan-abramov"  },
  {id:"96666", firstName: 'Mariana', image: "https://bit.ly/dan-abramov" },
  {id:"1340",firstName: 'Isabel', image: "https://bit.ly/sage-adebayo"  },
  {id:"15551",firstName: 'José', image: "https://bit.ly/sage-adebayo"  },
  {id:"124444",firstName: 'Ricardo', image: "https://bit.ly/dan-abramov"  },

];

const newslist=[
  {id:"11111", title: 'hgjk', image: "https://bit.ly/dan-abramov" },
  {id:"22222",title: 'jk', image: "https://bit.ly/sage-adebayo"  },
  {id:"33333",title: 'jk', image: "https://bit.ly/sage-adebayo"  },
  {id:"44444",title: '5r6tu', image: "https://bit.ly/dan-abramov"  },
  {id:"5555", title: '6tuhkl', image: "https://bit.ly/dan-abramov" },
  {id:"6666",title: 'hfgvb ', image: "https://bit.ly/sage-adebayo"  },
  {id:"7777",title: ' nbm', image: "https://bit.ly/sage-adebayo"  },
  {id:"8888",title: 'cnmjh', image: "https://bit.ly/dan-abramov"  },
  {id:"9999", title: 'htfjgkhl', image: "https://bit.ly/dan-abramov" },
  {id:"11222",title: 'chjgh', image: "https://bit.ly/sage-adebayo"  },
  {id:"1331",title: 'chjk', image: "https://bit.ly/sage-adebayo"  },
  {id:"12444",title: 'fgjhk', image: "https://bit.ly/dan-abramov"  },
];



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
  const onSubmit = (data, e) => console.log(data, e);
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


  const handleDeleteTag = ()=>{


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
    ></ContentStep1>
  )
  
  const content2 = (
    <ContentStep2 users={users} />
  );

  const content3 = (
    <ContentStep3 newslist={newslist} />
  );

  const steps = [
    { label: 'Project Details', content: content1 },
    { label: 'Associate Users', content: content2 },
    { label: 'Associate News', content: content3 },
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
          <form onSubmit={ handleSubmit (onSubmit, onError)}>
            <Steps activeStep={activeStep}>
              {steps.map(({ label, content }) => (
                <Step label={label} key={label} >
                  {content}
                </Step>
              ))}
            </Steps>
        
            {activeStep === steps.length ? (
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
            )}
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