# JMeter results
## Registration test-  */auth/register*
### Instructions
- Load the `JMeter register test.jmx` to JMeter
- Start the application by runing `start.sh`
- Make sure that `user` table is empty before each test
- Modify Sampler for the tests  below
### 10 Requests to gateway
Everything is ok
![10 Results](/Register_gateway_10.png)

### 50 Requests to gateway
Took 32 seconds to respond to all 50 requests and 11 of them failed due too this error
``{"timestamp":1722342868217,"status":500,"error":"Internal Server Error","message":"Unable to acquire JDBC Connection","path":"/auth/register"}``
![50 Results](/Register_gateway_50.png)
### 100 Requests to gateway
Took 35 seconds to respond to all 100 requests and 9 are succefully performed, all other got the same error as above
![100 Results gateway](/Register_gateway_100.png)

### 100 Requests to identity microservice directly
Took 32 seconds to respond to all 100 requests and 50 are succefully performed, all other got the same error as above.
![100 Results gateway](/Register_directly_100.png)

### Conslusion
By looking at the results, we can see that calling a microservice directly performes better, so the possible problem could be at the gateway.

## Video streaming test-  */video/id/1*
### Instructions
- Load the `JMeter register test.jmx` to JMeter
- Start the application by runing `start.sh`
- Test [video](https://github.com/Ajaksmaniac/Streamify/blob/master/video-service/Files-Upload/1-first_video%20updated) is already existing in the repository
- Run previous test to generate a user
- Directly in the database create a channel
- Directly in the database create a video
- If Jmeter crashes, run jmeter in non gui mode

### 10 Requests
It tok 1.8 seconds to serve all 10 requests succefully
![Streaming 10](/Streaming_10.png)

### 50 Requests
It tok 9.2 seconds to serve all 50 requests succefully
![Streaming 50](/Streaming_50.png)

### 100 Requests
It tok 16 seconds to serve all 100 requests succefully
![Streaming 50](/Streaming_100.png)

### Conslusion
Streaming service performs well
