### Guide For Workflow Orchestrator 
Workflow orchestrator save all the alarms received into DB, it will trigger workflow 5 mins after the 1st valid alarm received.

After workflow orchestrator get 1 valid alarm, it will ignore subsequent same type alarm within 10 mins to avoid duplicate scale out.

#### deployment step
##### 1 upload WF-Orchestrator-0.0.1-SNAPSHOT-bin.zip to deploy server /root
##### 2 switch current user to root
```shell
su - root
```
##### 3 make dir for deploy
```shell
mkdir ca
```
##### 4 deploy
```shell
unzip WF-Orchestrator-0.0.1-SNAPSHOT-bin.zip
cp WF-Orchestrator-0.0.1-SNAPSHOT/* /root/ca/ 
rm -rf WF-Orchestrator-0.0.1-SNAPSHOT
rm -f WF-Orchestrator-0.0.1-SNAPSHOT-bin.zip
cd /root/ca
chmod +x geckodriver
```

##### 5 start
```shell
java -jar WF-Orchestrator-0.0.1-SNAPSHOT.jar
```

##### 6 stop
ctrl + c

##### 7 change configuration
we can change the time interval by modify last line as following in application.yaml, e.g. value 600 means 600 seconds(10 minutes)
  
```yaml
  ingoretimeinterval: 600
```
  
#### maintenance
##### 1 original alarm url with user admin/admin123
http://localhost:8088/wf-orchestrator/alarm

##### 2 aggregated alarm which will trigger workflow
http://localhost:8088/wf-orchestrator/handleAlarm

