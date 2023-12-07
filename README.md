# folder prod to upload
abdun.upload-image=/home/abdun/experiment/profile/tenant/

# upload quarkus
http = 8080
export GRAALVM_HOME=/home/app/mandrel-java17-23.0.1.2-Final/
quarkus build --native;
cd target/
tar -zcvf ./antre-quarkus-1.0.0-SNAPSHOT-runner.tar.gz antre-quarkus-1.0.0-SNAPSHOT-runner 
scp ./antre-quarkus-1.0.0-SNAPSHOT-runner.tar.gz root@135.181.41.212:/home/abdun/antre/quarkus/
tar -xzvf antre-quarkus-1.0.0-SNAPSHOT-runner.tar.gz

# upload web
change API on enironments.ts
ng build
cd /dist
tar -zcvf ./antre-web.tar.gz ./
scp ./antre-web.tar.gz  root@135.181.41.212:/home/abdun/antre/web
tar -xzvf antre-web.tar.gz
mv antre-web antrekuyy
