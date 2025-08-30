$PROJECT_ID="unisport-041219"
$REGION="asia-northeast3"
$SERVICE_NAME="unisport-api"
$SQL_INSTANCE="unisport-041219:asia-northeast3:unisport-instance"
$DB_NAME="unisport-db"
$DB_USER="unisport_user"
$SA_EMAIL="sebin-542@unisport-041219.iam.gserviceaccount.com"

gcloud run deploy $SERVICE_NAME `
  --source . `
  --region $REGION `
  --allow-unauthenticated `
  --service-account $SA_EMAIL `
  --set-env-vars "SPRING_PROFILES_ACTIVE=prod,DB_NAME=$DB_NAME,DB_USER=$DB_USER,SQL_INSTANCE=$SQL_INSTANCE" `
  --set-secrets "DB_PASSWORD=DB_PASSWORD:latest" `
  --min-instances=1
