import React from "react"
import { useState } from "react";
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
    InputRightElement
} from "@chakra-ui/react";
import {  AddIcon } from '@chakra-ui/icons'


import RedirectButton from "../components/RedirectButton";


const users = [
  {id:"1", firstName: 'Mariana', imageURL: "https://bit.ly/dan-abramov" },
  {id:"2",firstName: 'Isabel', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"3",firstName: 'José', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"4",firstName: 'Ricardo', imageURL: "https://bit.ly/dan-abramov"  },
  {id:"5", firstName: 'Mariana', imageURL: "https://bit.ly/dan-abramov" },
  {id:"6",firstName: 'Isabel', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"7",firstName: 'José', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"8",firstName: 'Ricardo', imageURL: "https://bit.ly/dan-abramov"  },
  {id:"9", firstName: 'Mariana', imageURL: "https://bit.ly/dan-abramov" },
  {id:"10",firstName: 'Isabel', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"11",firstName: 'José', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"12",firstName: 'Ricardo', imageURL: "https://bit.ly/dan-abramov"  },

];

const projectslist=[
  {id:"1", title: 'hgjk', imageURL: "https://bit.ly/dan-abramov" },
  {id:"2",title: 'jk', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"3",title: 'jk', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"4",title: '5r6tu', imageURL: "https://bit.ly/dan-abramov"  },
  {id:"5", title: '6tuhkl', imageURL: "https://bit.ly/dan-abramov" },
  {id:"6",title: 'hfgvb ', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"7",title: ' nbm', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"8",title: 'cnmjh', imageURL: "https://bit.ly/dan-abramov"  },
  {id:"9", title: 'htfjgkhl', imageURL: "https://bit.ly/dan-abramov" },
  {id:"10",title: 'chjgh', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"11",title: 'chjk', imageURL: "https://bit.ly/sage-adebayo"  },
  {id:"12",title: 'fgjhk', imageURL: "https://bit.ly/dan-abramov"  },

];

  //**********************************************MAIN FUNCTION !!!!!*************************************************************************** */

function NewNews() {
    //https://stackoverflow.com/questions/39630620/react-intl-how-to-use-formattedmessage-in-input-placeholder
    const intl = useIntl();



  const {register, handleSubmit, formState: {errors}}= useForm();
  const { nextStep, prevStep, setStep, reset, activeStep } = useSteps({
    initialStep: 0,
  });

  //const onSubmit = values => console.log(values); 
  //const handleSubmit(data){setData(data), console.log(data) )};

  const onSubmit = (data, e) => console.log(data, e);
  const onError = (errors, e) => console.log(errors, e);



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

  //**********************************************USE EFFECT*************************************************************************** */
 
  /**
     * use effect À escuta da variável que obriga ao scroll down
     */
   useEffect(() => {
    window.scrollTo(0,document.body.scrollHeight);
  },[scrollDown])
   

  const content1 = (
    <Flex justifyContent={"center"} py={4} width={"100%"}>
      {/* <Text p={1} >step 1 texto </Text> */}
      {/* <form onSubmit={ handleSubmit (onSubmit, onError)}> */}
      <Stack
          spacing={4}
          p={4}
          backgroundColor="whiteAlpha.900"
          boxShadow="md"
          width={"100%"}
      >
        <FormControl isInvalid = {errors.title}>
          <Input {...register("title", {required: true})} type="text" placeholder={intl.formatMessage({id: 'form_field_title'})} />
          {(errors.title)? 
            (<FormErrorMessage><FormattedMessage id={"error_missing_title"}  ></FormattedMessage></FormErrorMessage>)
            : null 
          }
        </FormControl>

        <Textarea {...register("description", {required: false})} backgroundColor="whiteAlpha.950" placeholder={intl.formatMessage({id: 'form_field_description'})}  />
        
        <FormControl isInvalid = {errors.keyword}>
        <InputGroup>
          <Input {...register("keywords", {required: true})} type="text" placeholder={intl.formatMessage({id: 'form_field_keywords'})}  onChange={saveInputKeyword} value={input} />

          <InputRightElement width="4.5rem">
              <Button h="1.75rem" size="sm" onClick={handleAddNewKeyword}>
                  {/* {showPassword ? "Hide" : "Show"} */}
                <AddIcon color = "gray.400" />
              </Button>
          </InputRightElement>
      </InputGroup>
      {keywords && keywords.length ?
       (<Box width={"l"} mr={3}> {keywords.map(k => ( <Badge key={k} display="inline-block" mx={1}>{k}</Badge>))} </Box>)
       :null
      }
       {console.log(keywords) }


        {(errors.keyword)? 
            (<FormErrorMessage><FormattedMessage id={"error_missing_keyword"}  ></FormattedMessage></FormErrorMessage>)
            : null 
          }
        </FormControl>

        <FormControl isInvalid = {errors.imageURL}>

          <Input {...register("imageURL")} type= "url" placeholder={intl.formatMessage({id: 'form_field_imageURL'})} />
          
          {/* TODO previsualizar imagem
          <Image
            boxSize='100px'
            objectFit='cover'
            src={register.imageURL}
            alt='project_Image'
          /> */}

          {(errors.password)? 
            (<FormErrorMessage><FormattedMessage id={"error_wrong_imageURL"}  ></FormattedMessage></FormErrorMessage>)
            : null 
          }
        
        </FormControl>

        {/* <Button
            borderRadius={0}
            type="submit"
            variant="solid"
            colorScheme="teal"
            width="full"
            // onClick={console.log("carreguei em login")}
        >
            {intl.formatMessage({id: 'create_project'})} 
        </Button> */}

      </Stack>
    {/* </form> */}
    </Flex>
  );
  const content2 = (
    <Flex justifyContent={"center"} py={4}  width={"100%"}>
      <Stack
      spacing={4}
      p={4}
      backgroundColor="whiteAlpha.900"
      boxShadow="md"
      width={"100%"}
      >
        <FormControl >
          <Grid  templateColumns='repeat(2, 1fr)' >
            {users.map(u => (
              <Checkbox key={u.id} colorScheme='teal' m={3} >
                <Tag size='lg' colorScheme='teal' borderRadius='full' variant="outline">
                  <Avatar
                    src={u.imageURL}
                    size='xs'
                    name={u.firstName}
                    ml={-1}
                    mr={2}
                  />
                  <TagLabel>{u.firstName}</TagLabel>
                </Tag>
              </Checkbox>
            ))}  
          </Grid>        
        </FormControl>
      </Stack>
    </Flex>
  );



  const content3 = (
    <Flex justifyContent={"center"} py={4}>
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
          {projectslist.map(n => (
            <Checkbox colorScheme='teal' m={3} key={n} >
              {n.title}
            </Checkbox>        
          ))}          
        </Grid>
        </FormControl>
      </Stack>
    </Flex>
  );
  const steps = [
    { label: 'Project Details', content: content1 },
    { label: 'Associate Users', content: content2 },
    { label: 'Associate Projects', content: content3 },
  ];




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
        
            {activeStep === steps.length ? (
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
                <RedirectButton path="/news" description="_back_to_news" />
            </Box>
        </Box>
      </Stack>
    
    </Flex>
  );
}

export default NewNews;